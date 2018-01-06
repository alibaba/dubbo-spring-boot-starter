package com.alibaba.boot.dubbo;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboServletAutoConfiguration implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

    private static final Logger LOG = LoggerFactory.getLogger(DubboAutoConfiguration.class);

    @Resource
    private DubboProperties properties;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        registerServlet(servletContext);
    }

    private void registerServlet(ServletContext servletContext) {
        String urlMapping = properties.getHttpProtocol().getContextpath() + "/*";
        LOG.info("begin to construct dubbo DispatcherServlet, urlMapping={}", urlMapping);

        com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet dubboServlet = new com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet();
        ServletRegistration.Dynamic serviceServlet = servletContext.addServlet("dubboServlet", dubboServlet);

        serviceServlet.addMapping(urlMapping);
        serviceServlet.setLoadOnStartup(2);

        LOG.info("construct dubbo DispatcherServlet complete, urlMapping={}", urlMapping);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {

    }
}