package com.alibaba.boot.dubbo;

import javax.annotation.Resource;

import com.alibaba.boot.dubbo.endpoint.DubboEndpoint;
import com.alibaba.boot.dubbo.endpoint.DubboOperationEndpoint;
import com.alibaba.boot.dubbo.health.DubboHealthIndicator;
import com.alibaba.boot.dubbo.metrics.DubboMetrics;
import com.alibaba.dubbo.config.ProtocolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(DubboAutoConfiguration.class);

    @Resource
    private DubboProperties properties;

    static {
        System.setProperty("dubbo.application.logger", "slf4j");
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
        fillDefaultForDubboProtocol(properties.getDubboProtocol());
    }

    private void fillDefaultForDubboProtocol(ProtocolConfig dubboProtocol) {

    }
}
