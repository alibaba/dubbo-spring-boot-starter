package com.alibaba.boot.dubbo;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ServiceBean;

/**
 * DubboProviderAutoConfiguration
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
public class DubboProviderAutoConfiguration {
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private DubboProperties properties;

  @Autowired
  private ApplicationConfig applicationConfig;

  @Autowired
  private ProtocolConfig protocolConfig;
  @Autowired
  private RegistryConfig registryConfig;

  @PostConstruct
  public void init() throws Exception {
    Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Service.class);
    for (Map.Entry<String, Object> entry : beans.entrySet()) {
      this.initProviderBean(entry.getKey(), entry.getValue());
    }
  }

  public void initProviderBean(String beanName, Object bean) throws Exception {
    Service service = this.applicationContext.findAnnotationOnBean(beanName, Service.class);
    ServiceBean<Object> serviceConfig = new ServiceBean<Object>(service);
    if (void.class.equals(service.interfaceClass()) && "".equals(service.interfaceName())) {
      if (bean.getClass().getInterfaces().length > 0) {
        serviceConfig.setInterface(bean.getClass().getInterfaces()[0]);
      } else {
        throw new IllegalStateException("Failed to export remote service class "
            + bean.getClass().getName()
            + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
      }
    }
    String version = service.version();
    if (version == null || "".equals(version)) {
      version = this.properties.getVersion();
    }
    if (version != null && !"".equals(version)) {
      serviceConfig.setVersion(version);
    }
    String group = service.group();
    if (group == null || "".equals(group)) {
      group = this.properties.getGroup();
    }
    if (group != null && !"".equals(group)) {
      serviceConfig.setGroup(group);
    }
    serviceConfig.setApplicationContext(this.applicationContext);
    serviceConfig.setApplication(this.applicationConfig);
    serviceConfig.setProtocol(this.protocolConfig);
    serviceConfig.setRegistry(this.registryConfig);
    serviceConfig.afterPropertiesSet();
    serviceConfig.setRef(bean);
    serviceConfig.export();
  }

}
