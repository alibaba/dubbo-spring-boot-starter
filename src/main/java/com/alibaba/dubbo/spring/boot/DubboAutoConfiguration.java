package com.alibaba.dubbo.spring.boot;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.spring.boot.health.DubboHealthIndicator;
import com.alibaba.dubbo.spring.boot.server.DubboServer;

/**
 * Dubbo common configuration
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration {
  @Autowired
  private DubboProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public ApplicationConfig dubboApplicationConfig(Environment environment) {
    String appname = this.properties.getAppname();
    if (appname == null) {
      appname = environment.getProperty("spring.application.name");
    }
    ApplicationConfig appConfig = new ApplicationConfig();
    appConfig.setName(appname);
    return appConfig;
  }

  /**
   * Start a non-daemon thread
   *
   * @since 1.0.2
   * @return DubboServer
   */
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(prefix = "spring.dubbo", name = "server", havingValue = "true")
  public DubboServer dubboServer() {
    final DubboServer dubboServer = new DubboServer();
    final CountDownLatch latch = new CountDownLatch(1);
    Thread awaitThread = new Thread("dubboServer") {

      @Override
      public void run() {
        latch.countDown();
        dubboServer.await();
      }
    };
    awaitThread.setContextClassLoader(this.getClass().getClassLoader());
    awaitThread.setDaemon(false);
    awaitThread.start();
    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }

    return dubboServer;
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(prefix = "spring.dubbo", name = "registry")
  public RegistryConfig dubboRegistryConfig() {
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setAddress(this.properties.getRegistry());
    return registryConfig;
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(prefix = "spring.dubbo", name = "monitor")
  public MonitorConfig dubboMonitorConfig() {
    MonitorConfig monitorConfig = new MonitorConfig();
    monitorConfig.setAddress(this.properties.getMonitor());
    return monitorConfig;
  }

  @Bean
  public DubboHealthIndicator dubboHealthIndicator() {
    return new DubboHealthIndicator();
  }
}
