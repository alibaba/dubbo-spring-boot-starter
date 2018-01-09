package com.alibaba.boot.dubbo.utils;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.boot.dubbo.DubboProperties;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;

public class DubboAutoConfigUtils {

    private DubboAutoConfigUtils() {

    }

    public static List<RegistryConfig> getRegistries(DubboProperties properties) {
        List<RegistryConfig> registries = new LinkedList<RegistryConfig>();
        if (properties.getZkRegistry() != null) {
            registries.add(properties.getZkRegistry());
        }
        if (properties.getRedisRegistry() != null) {
            registries.add(properties.getRedisRegistry());
        }
        if (CollectionUtils.isEmpty(registries)) {
            throw new IllegalStateException("dubbo registries can not be empty: " + properties);
        }
        return registries;
    }

    public static List<ProtocolConfig> getProtocols(DubboProperties properties) {
        List<ProtocolConfig> protocols = new LinkedList<ProtocolConfig>();
        if (properties.getDubboProtocol() != null) {
            protocols.add(properties.getDubboProtocol());
        }
        if (properties.getRestProtocol() != null) {
            protocols.add(properties.getRestProtocol());
        }
        if (CollectionUtils.isEmpty(protocols)) {
            throw new IllegalStateException("dubbo protocols can not be empty: " + properties);
        }
        return protocols;
    }
}
