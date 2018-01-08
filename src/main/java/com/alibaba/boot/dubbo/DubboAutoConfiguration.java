package com.alibaba.boot.dubbo;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;

import com.alibaba.boot.dubbo.endpoint.DubboEndpoint;
import com.alibaba.boot.dubbo.endpoint.DubboOperationEndpoint;
import com.alibaba.boot.dubbo.health.DubboHealthIndicator;
import com.alibaba.boot.dubbo.metrics.DubboMetrics;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(DubboAutoConfiguration.class);

    static {
        System.setProperty("dubbo.application.logger", "slf4j");
    }

    @Bean
    @ConditionalOnMissingBean
    public static ApplicationConfig applicationConfig(DubboProperties properties) {
        ApplicationConfig application = properties.getApplication();
        List<RegistryConfig> registries = new LinkedList<>();
        registries.add(properties.getZkRegistry());
        registries.add(properties.getRedisRegistry());
        application.setRegistries(registries);
        application.setMonitor(properties.getMonitor());
        return application;
    }

    @Bean
    @ConditionalOnMissingBean
    public static ProtocolConfig dubboProtocolConfig(DubboProperties properties) {
        return properties.getDubboProtocol();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProtocolConfig httpProtocolConfig(DubboProperties properties) {
        return properties.getHttpProtocol();
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig zkRegistryConfig(DubboProperties properties) {
        return properties.getZkRegistry();
    }

    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig redisRegistryConfig(DubboProperties properties) {
        return properties.getRedisRegistry();
    }

    @Bean
    public static DubboHealthIndicator dubboHealthIndicator() {
        DubboHealthIndicator healthIndicator = new DubboHealthIndicator();
        LOG.info("construct dubbo health indicator complete");
        return healthIndicator;
    }

    @Bean
    public static DubboEndpoint dubboEndpoint() {
        DubboEndpoint dubboEndpoint = new DubboEndpoint();
        LOG.info("construct dubbo endpoint complete");
        return dubboEndpoint;
    }

    @Bean
    public static DubboMetrics dubboConsumerMetrics() {
        DubboMetrics dubboMetrics = new DubboMetrics();
        LOG.info("construct dubbo metrics complete");
        return dubboMetrics;
    }


    @Bean
    public static DubboOperationEndpoint dubboOperationEndpoint() {
        DubboOperationEndpoint dubboOperationEndpoint = new DubboOperationEndpoint();
        LOG.info("construct dubbo operation endpoint complete");
        return dubboOperationEndpoint;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        fillDefaultForMonitor(properties.getMonitor());
    }

//    private void fillDefaultForMonitor(MonitorConfig monitor) {
//        if (monitor != null) {
//            monitor.setProtocol(StringUtils.isBlank(monitor.getProtocol()) ? "registry" : monitor.getProtocol());
//            monitor.setDefault(true);
//        }
//    }
}
