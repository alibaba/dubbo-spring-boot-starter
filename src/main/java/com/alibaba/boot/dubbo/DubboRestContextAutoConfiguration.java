package com.alibaba.boot.dubbo;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.alibaba.dubbo.remoting.http.servlet.BootstrapListener;
import com.alibaba.dubbo.remoting.http.servlet.ServletManager;
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
public class DubboRestContextAutoConfiguration implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

    private static final Logger LOG = LoggerFactory.getLogger(DubboAutoConfiguration.class);

    @Resource
    private DubboProperties properties;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        registerListeners(servletContext);

        registerServlets(servletContext);
    }

    private void registerListeners(ServletContext servletContext) {
        String urlMapping = properties.getHttpProtocol().getContextpath() + "/*";
        LOG.info("begin to construct dubbo BootstrapListener, urlMapping={}", urlMapping);

        com.alibaba.dubbo.remoting.http.servlet.BootstrapListener listener = new com.alibaba.dubbo.remoting.http.servlet.BootstrapListener();
        servletContext.addListener(listener);

        LOG.info("construct dubbo BootstrapListener complete, urlMapping={}", urlMapping);
    }

    private void registerServlets(ServletContext servletContext) {
        String urlMapping = properties.getHttpProtocol().getContextpath() + "/*";
        LOG.info("begin to construct dubbo DispatcherServlet, urlMapping={}", urlMapping);

        com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet dubboServlet = new com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet();
        ServletRegistration.Dynamic serviceServlet = servletContext.addServlet("dispatcher", dubboServlet);

        serviceServlet.addMapping(urlMapping);
        serviceServlet.setLoadOnStartup(1);

        LOG.info("construct dubbo DispatcherServlet complete, urlMapping={}", urlMapping);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
    }

}