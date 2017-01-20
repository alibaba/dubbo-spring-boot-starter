package com.alibaba.boot.dubbo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.alibaba.boot.dubbo.domain.ClassIdBean;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ReferenceBean;

/**
 * DubboConsumerAutoConfiguration, use {@link Service#version} and {@link Service#timeout}
 * properties.
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(Service.class)
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboConsumerAutoConfiguration {
  public static final Map<ClassIdBean, Object> DUBBO_REFERENCES_MAP =
      new ConcurrentHashMap<ClassIdBean, Object>();

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private DubboProperties properties;

  @Autowired
  private ApplicationConfig applicationConfig;
  @Autowired
  private RegistryConfig registryConfig;

  @Bean
  public BeanPostProcessor beanPostProcessor() {
    return new BeanPostProcessor() {
      @Override
      public Object postProcessBeforeInitialization(Object bean, String beanName)
          throws BeansException {
        Class<?> objClz = bean.getClass();
        if (org.springframework.aop.support.AopUtils.isAopProxy(bean)) {
          objClz = org.springframework.aop.support.AopUtils.getTargetClass(bean);
        }

        try {
          for (Field field : objClz.getDeclaredFields()) {
            DubboConsumer dubboConsumer = field.getAnnotation(DubboConsumer.class);
            if (dubboConsumer != null) {
              Class<?> type = field.getType();
              ReferenceBean<?> consumerBean =
                  DubboConsumerAutoConfiguration.this.getConsumerBean(type, dubboConsumer);
              String group = consumerBean.getGroup();
              String version = consumerBean.getVersion();
              ClassIdBean classIdBean = new ClassIdBean(type, group, version);
              Object dubboReference =
                  DubboConsumerAutoConfiguration.DUBBO_REFERENCES_MAP.get(classIdBean);
              if (dubboReference == null) {
                synchronized (this) {
                  // double check
                  dubboReference =
                      DubboConsumerAutoConfiguration.DUBBO_REFERENCES_MAP.get(classIdBean);
                  if (dubboReference == null) {
                    consumerBean.setApplicationContext(
                        DubboConsumerAutoConfiguration.this.applicationContext);
                    consumerBean
                        .setApplication(DubboConsumerAutoConfiguration.this.applicationConfig);
                    RegistryConfig registry = consumerBean.getRegistry();
                    if (registry == null) {
                      consumerBean.setRegistry(DubboConsumerAutoConfiguration.this.registryConfig);
                    }
                    consumerBean
                        .setApplication(DubboConsumerAutoConfiguration.this.applicationConfig);
                    consumerBean.afterPropertiesSet();
                    // 理论上dubboReference不能为空，否则就会抛NullPointerException了
                    dubboReference = consumerBean.getObject();
                    DubboConsumerAutoConfiguration.DUBBO_REFERENCES_MAP.put(classIdBean,
                        dubboReference);
                  }
                }
              }
              field.setAccessible(true);
              field.set(bean, dubboReference);
              field.setAccessible(false);
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
   * 设置相关配置信息, @see DubboConsumer
   *
   * @param interfaceClazz
   * @param dubboConsumer
   * @return
   * @throws BeansException
   */
  private <T> ReferenceBean<T> getConsumerBean(Class<T> interfaceClazz, DubboConsumer dubboConsumer)
      throws BeansException {
    ReferenceBean<T> consumerBean = new ReferenceBean<T>();
    consumerBean.setInterface(interfaceClazz);
    String canonicalName = interfaceClazz.getCanonicalName();
    consumerBean.setId(canonicalName);
    String registry = dubboConsumer.registry();
    if (registry != null && registry.length() > 0) {
      RegistryConfig registryConfig = new RegistryConfig();
      registryConfig.setAddress(registry);
      consumerBean.setRegistry(registryConfig);
    }
    String group = dubboConsumer.group();
    if (group == null || "".equals(group)) {
      group = this.properties.getGroup();
    }
    if (group != null && !"".equals(group)) {
      consumerBean.setGroup(group);
    }
    String version = dubboConsumer.version();
    if (version == null || "".equals(version)) {
      version = this.properties.getVersion();
    }
    if (version != null && !"".equals(version)) {
      consumerBean.setVersion(version);
    }
    int timeout = dubboConsumer.timeout();
    consumerBean.setTimeout(timeout);
    String client = dubboConsumer.client();
    consumerBean.setClient(client);
    String url = dubboConsumer.url();
    consumerBean.setUrl(url);
    String protocol = dubboConsumer.protocol();
    consumerBean.setProtocol(protocol);
    boolean check = dubboConsumer.check();
    consumerBean.setCheck(check);
    boolean lazy = dubboConsumer.lazy();
    consumerBean.setLazy(lazy);
    int retries = dubboConsumer.retries();
    consumerBean.setRetries(retries);
    int actives = dubboConsumer.actives();
    consumerBean.setActives(actives);
    String loadbalance = dubboConsumer.loadbalance();
    consumerBean.setLoadbalance(loadbalance);
    boolean async = dubboConsumer.async();
    consumerBean.setAsync(async);
    boolean sent = dubboConsumer.sent();
    consumerBean.setSent(sent);
    return consumerBean;
  }
}
