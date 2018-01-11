package com.alibaba.boot.dubbo;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.alibaba.boot.dubbo.utils.DubboAutoConfigUtils;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.boot.dubbo.annotation.ServiceMethod;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Service.class)
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboProviderAutoConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(DubboProviderAutoConfiguration.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DubboProperties properties;

    @PostConstruct
    public void init() throws Exception {
        LOG.info("export services use config: {}", properties);
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Service.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            this.initProviderBean(entry.getKey(), entry.getValue());
        }
    }

    public void initProviderBean(String beanName, Object bean) throws Exception {
        LOG.info("begin to construct provider bean, beanName={}, bean={}", beanName, bean);
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

        if (StringUtils.isBlank(serviceConfig.getVersion())) {
            String version =
                    StringUtils.isNotEmpty(service.version()) ? service.version() : this.properties.getVersion();
            serviceConfig.setVersion(version);
        }

        if (StringUtils.isBlank(serviceConfig.getGroup())) {
            String group = StringUtils.isNotEmpty(service.group()) ? service.group() : this.properties.getGroup();
            serviceConfig.setGroup(group);
        }

        serviceConfig.setApplicationContext(this.applicationContext);
        serviceConfig.setApplication(this.properties.getApplication());
        if (CollectionUtils.isEmpty(serviceConfig.getProtocols())) {
            serviceConfig.setProtocols(DubboAutoConfigUtils.getProtocols(this.properties));
        }
        if (CollectionUtils.isEmpty(serviceConfig.getRegistries())) {
            serviceConfig.setRegistries(DubboAutoConfigUtils.getRegistries(this.properties));
        }
        if (serviceConfig.getMonitor() == null) {
            serviceConfig.setMonitor(this.properties.getMonitor());
        }

        // 方法级配置（只能标注在实现类上，不能标注在方法上；如果标注在方法上，则不生效）
        List<MethodConfig> configs = new LinkedList<>();
        Class beanClass = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean) : bean.getClass();
        Method[] methods = beanClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ServiceMethod.class)) {
                ServiceMethod def = method.getAnnotation(ServiceMethod.class);
                MethodConfig config = new MethodConfig();
                config.setName(method.getName());
                config.setTimeout(def.timeout());
                config.setRetries(def.retries());
                config.setLoadbalance(def.loadbalance());
                config.setActives(def.actives());
                config.setValidation(def.validation());
                configs.add(config);

                LOG.debug("load method annotation: beanClass={}, methodConfig={}", beanClass.getCanonicalName(), config);
            }
        }

        if (CollectionUtils.isNotEmpty(configs)) {
            serviceConfig.setMethods(configs);
        }

        serviceConfig.afterPropertiesSet();
        serviceConfig.setRef(bean);
        serviceConfig.export();

        LOG.info("export provider bean complete, beanName={}, bean={}, serviceConfig={}", beanName, bean,
                serviceConfig);
    }

}
