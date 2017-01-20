package com.alibaba.boot.dubbo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * dubbo properties
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.dubbo")
public class DubboProperties {
  /**
   * dubbo application name
   */
  private String appname;
  /**
   * dubbo registry address
   */
  private String registry;
  /**
   * communication protocol, default is dubbo
   */
  private String protocol = "dubbo";
  /**
   * dubbo listen port, default 20800
   */
  private int port = 20800;
  /**
   * dubbo thread count, default 200
   */
  private int threads = 200;

  /**
   * dubbo version, may override by {@link com.alibaba.dubbo.config.annotation.Service#version()}
   */
  private String version = "";

  /**
   * dubbo group, may override by {@link com.alibaba.dubbo.config.annotation.Service#group()}
   */
  private String group = "";

  public String getAppname() {
    return this.appname;
  }

  public void setAppname(String appname) {
    this.appname = appname;
  }

  public String getRegistry() {
    return this.registry;
  }

  public void setRegistry(String registry) {
    this.registry = registry;
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

  @Override
  public String toString() {
    return "DubboProperties [appname=" + this.appname + ", registry=" + this.registry
        + ", protocol=" + this.protocol + ", port=" + this.port + ", threads=" + this.threads
        + ", version=" + this.version + ", group=" + this.group + "]";
  }
}
