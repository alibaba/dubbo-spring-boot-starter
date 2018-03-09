package com.alibaba.dubbo.spring.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

/**
 * DubboProviderAutoConfiguration
 *
 * @author xionghui
 * @author 韩旺坤
 * @version 1.0.0
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
  @Autowired(required = false)
  private RegistryConfig registryConfig;
  @Autowired(required = false)
  private MonitorConfig monitorConfig;

  @PostConstruct
  public void init() throws Exception {
    Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Service.class);
    for (Map.Entry<String, Object> entry : beans.entrySet()) {
      this.initProviderBean(entry.getKey(), entry.getValue());
    }
  }

  private void initProviderBean(String beanName, Object bean) throws Exception {
    Service service = this.applicationContext.findAnnotationOnBean(beanName, Service.class);
    ServiceBean<Object> serviceConfig = new ServiceBean<Object>(service);
    if ((service.interfaceClass() == null || service.interfaceClass() == void.class)
        && (service.interfaceName() == null || "".equals(service.interfaceName()))) {
      Class<?>[] interfaces = bean.getClass().getInterfaces();
      if (interfaces.length == 1) {
        serviceConfig.setInterface(interfaces[0]);
      }
    }
    String version = service.version();
    if (version == null || "".equals(version)) {
      version = this.properties.getVersion();
      if (version != null && !"".equals(version)) {
        serviceConfig.setVersion(version);
      }
    }
    String group = service.group();
    if (group == null || "".equals(group)) {
      group = this.properties.getGroup();
      if (group != null && !"".equals(group)) {
        serviceConfig.setGroup(group);
      }
    }

    String[] registrys = service.registry();
    if (registrys != null && registrys.length > 0) {
      List<RegistryConfig> registrieList = new ArrayList<RegistryConfig>();
      for (String registry : registrys) {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(registry);
        registrieList.add(registryConfig);
      }
      serviceConfig.setRegistries(registrieList);
    } else {
      serviceConfig.setRegistry(this.registryConfig);
    }
    if (this.monitorConfig != null) {
      serviceConfig.setMonitor(this.monitorConfig);
    }

    if (this.properties.getProtocols().isEmpty()) {
      ProtocolConfig protocolConfig = new ProtocolConfig();
      protocolConfig.setName(this.properties.getProtocol());
      protocolConfig.setPort(this.properties.getPort());
      protocolConfig.setThreads(this.properties.getThreads());
      serviceConfig.setProtocol(protocolConfig);
    } else {
      // handle multi protocols
      List<ProtocolConfig> protocolConfigList = new ArrayList<ProtocolConfig>();
      String[] protocols = service.protocol();
      if (protocols != null && protocols.length > 0) {
        Map<String, ProtocolConfig> protocolMap = this.properties.getProtocols();
        for (String protocol : protocols) {
          ProtocolConfig protocolConfig = protocolMap.get(protocol);
          if (protocolConfig == null) {
            throw new NullPointerException(
                "beanName=" + beanName + ", protocol=" + protocol + " not found in protocols");
          }
          protocolConfigList.add(protocolConfig);
        }
      } else {
        protocolConfigList.addAll(this.properties.getProtocols().values());
      }
      serviceConfig.setProtocols(protocolConfigList);
    }

    ProviderConfig providerConfig = this.properties.getProvider();
    if (providerConfig != null) {
      serviceConfig.setProvider(providerConfig);
    }
    serviceConfig.setApplicationContext(this.applicationContext);
    serviceConfig.setApplication(this.applicationConfig);
    serviceConfig.afterPropertiesSet();
    serviceConfig.setRef(bean);
    serviceConfig.export();
  }
}
