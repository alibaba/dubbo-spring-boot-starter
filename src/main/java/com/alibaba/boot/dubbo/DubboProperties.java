package com.alibaba.boot.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.dubbo")
public class DubboProperties {

    private ApplicationConfig application;

    private RegistryConfig zkRegistry;

    private RegistryConfig redisRegistry;

    private ProtocolConfig dubboProtocol;

    private ProtocolConfig restProtocol;

    private MonitorConfig monitor;

    private String version;

    private String group;

    public ApplicationConfig getApplication() {
        return application;
    }

    public void setApplication(ApplicationConfig application) {
        this.application = application;
    }

    public RegistryConfig getZkRegistry() {
        return zkRegistry;
    }

    public void setZkRegistry(RegistryConfig zkRegistry) {
        this.zkRegistry = zkRegistry;
    }

    public RegistryConfig getRedisRegistry() {
        return redisRegistry;
    }

    public void setRedisRegistry(RegistryConfig redisRegistry) {
        this.redisRegistry = redisRegistry;
    }

    public ProtocolConfig getDubboProtocol() {
        return dubboProtocol;
    }

    public void setDubboProtocol(ProtocolConfig dubboProtocol) {
        this.dubboProtocol = dubboProtocol;
    }

    public ProtocolConfig getRestProtocol() {
        return restProtocol;
    }

    public void setRestProtocol(ProtocolConfig restProtocol) {
        this.restProtocol = restProtocol;
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "DubboProperties{" +
                "application=" + application +
                ", zkRegistry=" + zkRegistry +
                ", redisRegistry=" + redisRegistry +
                ", dubboProtocol=" + dubboProtocol +
                ", restProtocol=" + restProtocol +
                ", monitor=" + monitor +
                ", version=" + version +
                ", group=" + group +
                '}';
    }
}
