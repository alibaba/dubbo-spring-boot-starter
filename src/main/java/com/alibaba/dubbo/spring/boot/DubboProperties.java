package com.alibaba.dubbo.spring.boot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;

/**
 * Dubbo properties
 *
 * @author xionghui
 * @author 韩旺坤
 * @version 1.0.0
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.dubbo")
public class DubboProperties {
  /**
   * Dubbo application name
   */
  private String appname;

  /**
   * Dubbo application server
   */
  private boolean server;

  /**
   * Dubbo registry address
   */
  private String registry;

  /**
   * Dubbo monitor address
   */
  private String monitor;

  /**
   * Communication protocol, default is dubbo
   */
  private String protocol = "dubbo";

  /**
   * Dubbo listen port, default 20800
   */
  private int port = 20800;

  /**
   * Dubbo thread count, default 200
   */
  private int threads = 200;

  /**
   * Dubbo version, may override by {@link com.alibaba.dubbo.config.annotation.Service#version
   * Service.version} or {@link com.alibaba.dubbo.config.annotation.Reference#version
   * Reference.version}
   */
  private String version = "";

  /**
   * Dubbo group, may override by {@link com.alibaba.dubbo.config.annotation.Service#group
   * Service.group} or {@link com.alibaba.dubbo.config.annotation.Reference#group Reference.group}
   */
  private String group = "";

  /**
   * Dubbo protocols, used for multi protocols
   *
   * @since 1.0.2
   */
  private Map<String, ProtocolConfig> protocols = new HashMap<String, ProtocolConfig>();

  /**
   * Dubbo provider
   *
   * @since 1.0.3
   */
  private ProviderConfig provider;

  /**
   * Dubbo consumer
   *
   * @since 1.0.3
   */
  private ConsumerConfig consumer;

  public String getAppname() {
    return this.appname;
  }

  public void setAppname(String appname) {
    this.appname = appname;
  }

  public boolean getServer() {
    return this.server;
  }

  public void setServer(boolean server) {
    this.server = server;
  }

  public String getRegistry() {
    return this.registry;
  }

  public void setRegistry(String registry) {
    this.registry = registry;
  }

  public String getMonitor() {
    return this.monitor;
  }

  public void setMonitor(String monitor) {
    this.monitor = monitor;
  }

  public String getProtocol() {
    return this.protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public int getPort() {
    return this.port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getThreads() {
    return this.threads;
  }

  public void setThreads(int threads) {
    this.threads = threads;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getGroup() {
    return this.group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public Map<String, ProtocolConfig> getProtocols() {
    return this.protocols;
  }

  public void setProtocols(Map<String, ProtocolConfig> protocols) {
    this.protocols = protocols;
  }

  public ProviderConfig getProvider() {
    return this.provider;
  }

  public void setProvider(ProviderConfig provider) {
    this.provider = provider;
  }

  public ConsumerConfig getConsumer() {
    return this.consumer;
  }

  public void setConsumer(ConsumerConfig consumer) {
    this.consumer = consumer;
  }

  @Override
  public String toString() {
    return "DubboProperties [appname=" + this.appname + ", server=" + this.server + ", registry="
        + this.registry + ", monitor=" + this.monitor + ", protocol=" + this.protocol + ", port="
        + this.port + ", threads=" + this.threads + ", version=" + this.version + ", group="
        + this.group + ", protocols=" + this.protocols + ", provider=" + this.provider
        + ", consumer=" + this.consumer + "]";
  }
}
