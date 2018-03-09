package com.alibaba.dubbo.spring.boot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.alibaba.dubbo.spring.boot.domain.ClassIdBean;

/**
 * DubboConsumerAutoConfiguration
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(Service.class)
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboConsumerAutoConfiguration {
  private static final Map<ClassIdBean, Object> DUBBO_REFERENCES_MAP =
      new ConcurrentHashMap<ClassIdBean, Object>();

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private DubboProperties properties;

  @Autowired
  private ApplicationConfig applicationConfig;
  @Autowired(required = false)
  private RegistryConfig registryConfig;
  @Autowired(required = false)
  private MonitorConfig monitorConfig;

  public static Object getDubboReference(ClassIdBean classIdBean) {
    return DUBBO_REFERENCES_MAP.get(classIdBean);
  }

  @Bean
  public BeanPostProcessor beanPostProcessor() {
    return new BeanPostProcessor() {

      @Override
      public Object postProcessBeforeInitialization(Object bean, String beanName)
          throws BeansException {
        Class<?> objClz;
        if (AopUtils.isAopProxy(bean)) {
          objClz = AopUtils.getTargetClass(bean);
        } else {
          objClz = bean.getClass();
        }

        try {
          for (Field field : objClz.getDeclaredFields()) {
            Reference reference = field.getAnnotation(Reference.class);
            if (reference != null) {
              ReferenceBean<?> referenceBean =
                  DubboConsumerAutoConfiguration.this.getConsumerBean(field.getType(), reference);
              Class<?> interfaceClass = referenceBean.getInterfaceClass();
              String group = referenceBean.getGroup();
              String version = referenceBean.getVersion();
              ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
              Object dubboReference =
                  DubboConsumerAutoConfiguration.DUBBO_REFERENCES_MAP.get(classIdBean);
              if (dubboReference == null) {
                synchronized (this) {
                  // double check
                  dubboReference =
                      DubboConsumerAutoConfiguration.DUBBO_REFERENCES_MAP.get(classIdBean);
                  if (dubboReference == null) {
                    referenceBean.afterPropertiesSet();
                    // 理论上dubboReference不能为空，否则就会抛NullPointerException了
                    dubboReference = referenceBean.getObject();
                    DubboConsumerAutoConfiguration.DUBBO_REFERENCES_MAP.put(classIdBean,
                        dubboReference);
                  }
                }
              }
              field.setAccessible(true);
              field.set(bean, dubboReference);
            }
          }
        } catch (Exception e) {
          throw new BeanCreationException(beanName, e);
        }
        return bean;
      }

      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName)
          throws BeansException {
        return bean;
      }
    };
  }

  /**
   * 设置consumer相关配置信息
   *
   * @param interfaceClazz interfaceClazz
   * @param reference reference
   * @return ReferenceBean<T>
   * @throws BeansException BeansException
   */
  private <T> ReferenceBean<T> getConsumerBean(Class<T> interfaceClazz, Reference reference)
      throws BeansException {
    ReferenceBean<T> referenceBean = new ReferenceBean<T>(reference);
    if ((reference.interfaceClass() == null || reference.interfaceClass() == void.class)
        && (reference.interfaceName() == null || "".equals(reference.interfaceName()))) {
      referenceBean.setInterface(interfaceClazz);
    }
    String version = reference.version();
    if (version == null || "".equals(version)) {
      version = this.properties.getVersion();
      if (version != null && !"".equals(version)) {
        referenceBean.setVersion(version);
      }
    }
    String group = reference.group();
    if (group == null || "".equals(group)) {
      group = this.properties.getGroup();
      if (group != null && !"".equals(group)) {
        referenceBean.setGroup(group);
      }
    }

    String[] registrys = reference.registry();
    if (registrys != null && registrys.length > 0) {
      List<RegistryConfig> registrieList = new ArrayList<RegistryConfig>();
      for (String registry : registrys) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(registry);
        registrieList.add(registryConfig);
      }
      referenceBean.setRegistries(registrieList);
    } else {
      referenceBean.setRegistry(DubboConsumerAutoConfiguration.this.registryConfig);
    }
    if (this.monitorConfig != null) {
      referenceBean.setMonitor(this.monitorConfig);
    }
    ConsumerConfig consumerConfig = this.properties.getConsumer();
    if (consumerConfig != null) {
      referenceBean.setConsumer(consumerConfig);
    }
    referenceBean.setApplicationContext(DubboConsumerAutoConfiguration.this.applicationContext);
    referenceBean.setApplication(DubboConsumerAutoConfiguration.this.applicationConfig);
    return referenceBean;
  }
}
