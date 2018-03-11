package com.alibaba.dubbo.spring.boot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * Dubbo common configuration
 *
 * @author xionghui
 * @version 2.0.0
 * @since 2.0.0
 */
public class DubboCommonAutoConfiguration {
  protected static Map<String, Map<String, AbstractConfig>> ID_CONFIG_MAP = null;

  protected static String APPLICATIONS_KEY = "applications";
  protected static String MODULES_KEY = "modules";
  protected static String REGISTRIES_KEY = "registries";
  protected static String PROTOCOLS_KEY = "protocols";
  protected static String MONITORS_KEY = "monitors";
  protected static String PROVIDERS_KEY = "providers";
  protected static String CONSUMERS_KEY = "consumers";

  protected void initIdConfigMap(DubboProperties dubboProperties) {
    if (ID_CONFIG_MAP != null) {
      return;
    }
    synchronized (DubboCommonAutoConfiguration.class) {
      // double check
      if (DubboCommonAutoConfiguration.ID_CONFIG_MAP != null) {
        return;
      }
      DubboCommonAutoConfiguration.ID_CONFIG_MAP =
          new HashMap<String, Map<String, AbstractConfig>>();
      Map<String, AbstractConfig> applications = new LinkedHashMap<String, AbstractConfig>();
      DubboCommonAutoConfiguration.ID_CONFIG_MAP.put(APPLICATIONS_KEY, applications);
      Map<String, ApplicationConfig> applicationMap = dubboProperties.getApplications();
      if (applicationMap != null) {
        for (Map.Entry<String, ApplicationConfig> entry : applicationMap.entrySet()) {
          ApplicationConfig value = entry.getValue();
          applications.put(value.getId(), value);
        }
      }

      Map<String, AbstractConfig> modules = new LinkedHashMap<String, AbstractConfig>();
      DubboCommonAutoConfiguration.ID_CONFIG_MAP.put(MODULES_KEY, modules);
      Map<String, ModuleConfig> moduleMap = dubboProperties.getModules();
      if (moduleMap != null) {
        for (Map.Entry<String, ModuleConfig> entry : moduleMap.entrySet()) {
          ModuleConfig value = entry.getValue();
          modules.put(value.getId(), value);
        }
      }

      Map<String, AbstractConfig> registries = new LinkedHashMap<String, AbstractConfig>();
      DubboCommonAutoConfiguration.ID_CONFIG_MAP.put(REGISTRIES_KEY, registries);
      Map<String, RegistryConfig> registryMap = dubboProperties.getRegistries();
      if (registryMap != null) {
        for (Map.Entry<String, RegistryConfig> entry : registryMap.entrySet()) {
          RegistryConfig value = entry.getValue();
          registries.put(value.getId(), value);
        }
      }

      Map<String, AbstractConfig> protocols = new LinkedHashMap<String, AbstractConfig>();
      DubboCommonAutoConfiguration.ID_CONFIG_MAP.put(PROTOCOLS_KEY, protocols);
      Map<String, ProtocolConfig> protocolMap = dubboProperties.getProtocols();
      if (protocolMap != null) {
        for (Map.Entry<String, ProtocolConfig> entry : protocolMap.entrySet()) {
          ProtocolConfig value = entry.getValue();
          protocols.put(value.getId(), value);
        }
      }

      Map<String, AbstractConfig> monitors = new LinkedHashMap<String, AbstractConfig>();
      DubboCommonAutoConfiguration.ID_CONFIG_MAP.put(MONITORS_KEY, monitors);
      Map<String, MonitorConfig> monitorMap = dubboProperties.getMonitors();
      if (monitorMap != null) {
        for (Map.Entry<String, MonitorConfig> entry : monitorMap.entrySet()) {
          MonitorConfig value = entry.getValue();
          monitors.put(value.getId(), value);
        }
      }

      Map<String, AbstractConfig> providers = new LinkedHashMap<String, AbstractConfig>();
      DubboCommonAutoConfiguration.ID_CONFIG_MAP.put(PROVIDERS_KEY, providers);
      Map<String, ProviderConfig> providerMap = dubboProperties.getProviders();
      if (providerMap != null) {
        for (Map.Entry<String, ProviderConfig> entry : providerMap.entrySet()) {
          ProviderConfig value = entry.getValue();
          providers.put(value.getId(), value);
        }
      }

      Map<String, AbstractConfig> consumers = new LinkedHashMap<String, AbstractConfig>();
      DubboCommonAutoConfiguration.ID_CONFIG_MAP.put(CONSUMERS_KEY, consumers);
      Map<String, ConsumerConfig> consumerMap = dubboProperties.getConsumers();
      if (consumerMap != null) {
        for (Map.Entry<String, ConsumerConfig> entry : consumerMap.entrySet()) {
          ConsumerConfig value = entry.getValue();
          consumers.put(value.getId(), value);
        }
      }
    }
  }

  protected String buildErrorMsg(String... errors) {
    throw new UnsupportedOperationException("Not support");
  }

  protected ApplicationConfig parseApplication(String application, DubboProperties properties,
      Environment environment, String... errors) {
    ApplicationConfig applicationConfig = null;
    if (application == null || "".equals(application)) {
      applicationConfig = properties.getApplication();
      if (applicationConfig == null) {
        applicationConfig = new ApplicationConfig();
        applicationConfig.setName(environment.getProperty("spring.application.name"));
      }
    } else {
      application = environment.resolvePlaceholders(application);
      Map<String, AbstractConfig> applicationMap = ID_CONFIG_MAP.get(APPLICATIONS_KEY);
      applicationConfig = (ApplicationConfig) applicationMap.get(application);
      if (applicationConfig == null) {
        applicationConfig = properties.getApplications().get(application);
        if (applicationConfig == null) {
          throw new NullPointerException(this.buildErrorMsg(errors));
        }
      }
    }
    return applicationConfig;
  }

  protected ModuleConfig parseModule(String module, DubboProperties properties,
      Environment environment, String... errors) {
    ModuleConfig moduleConfig = null;
    if (module == null || "".equals(module)) {
      moduleConfig = properties.getModule();
    } else {
      module = environment.resolvePlaceholders(module);
      Map<String, AbstractConfig> moduleMap = ID_CONFIG_MAP.get(MODULES_KEY);
      moduleConfig = (ModuleConfig) moduleMap.get(module);
      if (moduleConfig == null) {
        moduleConfig = properties.getModules().get(module);
        if (moduleConfig == null) {
          throw new NullPointerException(this.buildErrorMsg(errors));
        }
      }
    }
    return moduleConfig;
  }

  protected List<RegistryConfig> parseRegistries(String[] registries, DubboProperties properties,
      Environment environment, String... errors) {
    List<RegistryConfig> registryList = null;
    if (registries == null || registries.length == 0) {
      RegistryConfig registry = properties.getRegistry();
      if (registry != null) {
        registryList = new ArrayList<RegistryConfig>();
        registryList.add(registry);
      }
    } else {
      for (int i = 0, len = registries.length; i < len; i++) {
        registries[i] = environment.resolvePlaceholders(registries[i]);
      }
      registryList = new ArrayList<RegistryConfig>();
      Map<String, AbstractConfig> registryMap = ID_CONFIG_MAP.get(REGISTRIES_KEY);
      for (String registry : registries) {
        RegistryConfig registryConfig = (RegistryConfig) registryMap.get(registry);
        if (registryConfig == null) {
          registryConfig = properties.getRegistries().get(registry);
          if (registryConfig == null) {
            List<String> errorList = new ArrayList<String>();
            if (errors != null) {
              for (String error : errors) {
                errorList.add(error);
              }
            }
            errorList.add(registry);
            throw new NullPointerException(this.buildErrorMsg(errorList.toArray(new String[0])));
          }
        }
        registryList.add(registryConfig);
      }
    }
    return registryList;
  }

  protected List<ProtocolConfig> parseProtocols(String[] protocols, DubboProperties properties,
      Environment environment, String... errors) {
    List<ProtocolConfig> protocolList = null;
    if (protocols != null && protocols.length == 0) {
      ProtocolConfig protocol = properties.getProtocol();
      if (protocol != null) {
        protocolList = new ArrayList<ProtocolConfig>();
        protocolList.add(protocol);
      }
    } else {
      for (int i = 0, len = protocols.length; i < len; i++) {
        protocols[i] = environment.resolvePlaceholders(protocols[i]);
      }
      protocolList = new ArrayList<ProtocolConfig>();
      Map<String, AbstractConfig> protocolMap = ID_CONFIG_MAP.get(PROTOCOLS_KEY);
      for (String protocol : protocols) {
        ProtocolConfig protocolConfig = (ProtocolConfig) protocolMap.get(protocol);
        if (protocolConfig == null) {
          protocolConfig = properties.getProtocols().get(protocol);
          if (protocolConfig == null) {
            List<String> errorList = new ArrayList<String>();
            if (errors != null) {
              for (String error : errors) {
                errorList.add(error);
              }
            }
            errorList.add(protocol);
            throw new NullPointerException(this.buildErrorMsg(errorList.toArray(new String[0])));
          }
        }
        protocolList.add(protocolConfig);
      }
    }
    return protocolList;
  }

  protected MonitorConfig parseMonitor(String monitor, DubboProperties properties,
      Environment environment, String... errors) {
    MonitorConfig monitorConfig = null;
    if (monitor == null || "".equals(monitor)) {
      monitorConfig = properties.getMonitor();
    } else {
      monitor = environment.resolvePlaceholders(monitor);
      Map<String, AbstractConfig> monitorMap = ID_CONFIG_MAP.get(MONITORS_KEY);
      monitorConfig = (MonitorConfig) monitorMap.get(monitor);
      if (monitorConfig == null) {
        monitorConfig = properties.getMonitors().get(monitor);
        if (monitorConfig == null) {
          throw new NullPointerException(this.buildErrorMsg(errors));
        }
      }
    }
    return monitorConfig;
  }

  protected ProviderConfig parseProvider(String provider, DubboProperties properties,
      Environment environment, String... errors) {
    ProviderConfig providerConfig = null;
    if (provider == null || "".equals(provider)) {
      providerConfig = properties.getProvider();
    } else {
      provider = environment.resolvePlaceholders(provider);
      Map<String, AbstractConfig> providerMap = ID_CONFIG_MAP.get(PROVIDERS_KEY);
      providerConfig = (ProviderConfig) providerMap.get(provider);
      if (providerConfig == null) {
        providerConfig = properties.getProviders().get(provider);
        if (providerConfig == null) {
          throw new NullPointerException(this.buildErrorMsg(errors));
        }
      }
    }
    return providerConfig;
  }

  protected ConsumerConfig parseConsumer(String consumer, DubboProperties properties,
      Environment environment, String... errors) {
    ConsumerConfig consumerConfig = null;
    if (consumer == null || "".equals(consumer)) {
      consumerConfig = properties.getConsumer();
    } else {
      consumer = environment.resolvePlaceholders(consumer);
      Map<String, AbstractConfig> consumerMap = ID_CONFIG_MAP.get(CONSUMERS_KEY);
      consumerConfig = (ConsumerConfig) consumerMap.get(consumer);
      if (consumerConfig == null) {
        consumerConfig = properties.getConsumers().get(consumer);
        if (consumerConfig == null) {
          throw new NullPointerException(this.buildErrorMsg(errors));
        }
      }
    }
    return consumerConfig;
  }
}
