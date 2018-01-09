package com.alibaba.boot.dubbo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.alibaba.boot.dubbo.domain.ClassIdBean;
import com.alibaba.boot.dubbo.utils.DubboAutoConfigUtils;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Constants;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Configuration
@ConditionalOnClass(Service.class)
@ConditionalOnBean(annotation = EnableDubboConfiguration.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
@EnableConfigurationProperties(DubboProperties.class)
public class DubboConsumerAutoConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(DubboConsumerAutoConfiguration.class);

    public static final Map<ClassIdBean, Object> DUBBO_REFERENCES_MAP =
            new ConcurrentHashMap<ClassIdBean, Object>();

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private DubboProperties properties;

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        LOG.info("reference services use config: {}", properties);

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
                        Reference reference = field.getAnnotation(Reference.class);
                        if (reference != null) {
                            Class<?> type = field.getType();
                            ReferenceBean<?> consumerBean =
                                    DubboConsumerAutoConfiguration.this.getConsumerBean(type, reference);
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

                                        if (consumerBean.getApplication() == null) {
                                            consumerBean.setApplication(
                                                    DubboConsumerAutoConfiguration.this.properties.getApplication());
                                        }

                                        if (consumerBean.getRegistry() == null) {
                                            consumerBean.setRegistry(DubboConsumerAutoConfiguration.this.properties.getZkRegistry());
                                        }

                                        if (consumerBean.getMonitor() == null) {
                                            consumerBean.setMonitor(
                                                    DubboConsumerAutoConfiguration.this.properties.getMonitor());
                                        }

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
     * 设置相关配置信息,
     *
     * @param interfaceClazz
     * @param reference
     * @return
     * @throws BeansException
     * @see com.alibaba.dubbo.config.annotation.Reference
     */
    private <T> ReferenceBean<T> getConsumerBean(Class<T> interfaceClazz, Reference reference)
            throws BeansException {
        LOG.info("begin to construct consumer bean, interfaceClazz={}, reference={}",
                interfaceClazz.getCanonicalName(), reference);
        ReferenceBean<T> consumerBean = new ReferenceBean<T>();
        consumerBean.setInterface(interfaceClazz);
        String canonicalName = interfaceClazz.getCanonicalName();
        consumerBean.setId(canonicalName);

        if (StringUtils.isBlank(consumerBean.getVersion())) {
            String version = StringUtils.isNotEmpty(reference.version()) ? reference.version()
                    : this.properties.getVersion();
            consumerBean.setVersion(version);
        }

        if (StringUtils.isBlank(consumerBean.getGroup())) {
            String group = StringUtils.isNotEmpty(reference.group()) ? reference.group()
                    : this.properties.getGroup();
            consumerBean.setGroup(group);
        }

        if (consumerBean.getApplication() == null) {
            consumerBean.setApplication(DubboConsumerAutoConfiguration.this.properties.getApplication());
        }

        if (consumerBean.getRegistry() == null) {
            consumerBean.setRegistry(DubboConsumerAutoConfiguration.this.properties.getZkRegistry());
        }

        if (consumerBean.getMonitor() == null) {
            consumerBean.setMonitor(DubboConsumerAutoConfiguration.this.properties.getMonitor());
        }

        int timeout = reference.timeout();
        consumerBean.setTimeout(timeout);
        String client = reference.client();
        consumerBean.setClient(client);
        String url = reference.url();
        consumerBean.setUrl(url);
        consumerBean.setProtocol(Constants.PROTOCOL_DUBBO);
        boolean check = reference.check();
        consumerBean.setCheck(check);
        boolean lazy = reference.lazy();
        consumerBean.setLazy(lazy);
        int retries = reference.retries();
        consumerBean.setRetries(retries);
        int actives = reference.actives();
        consumerBean.setActives(actives);
        String loadbalance = reference.loadbalance();
        consumerBean.setLoadbalance(loadbalance);
        boolean async = reference.async();
        consumerBean.setAsync(async);
        boolean sent = reference.sent();
        consumerBean.setSent(sent);

        LOG.info("construct consumer bean complete, interfaceClazz={}, reference={}, consumerBean={}",
                interfaceClazz.getCanonicalName(), reference, consumerBean);
        return consumerBean;
    }
}
