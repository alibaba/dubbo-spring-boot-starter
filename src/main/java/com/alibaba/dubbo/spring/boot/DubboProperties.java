package com.alibaba.dubbo.spring.boot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.dubbo.config.ProtocolConfig;

/**
 * dubbo properties
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.dubbo")
public class DubboProperties {
  /**
   * dubbo application name
   */
  private String appname;

  /**
   * dubbo application server
   */
  private boolean server;

  /**
   * dubbo registry address
   */
  private String registry;

  /**
   * dubbo monitor address
   */
  private String monitor;

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
   * dubbo version, may override by {@link com.alibaba.dubbo.config.annotation.Service#version
   * Service.version} or {@link com.alibaba.dubbo.config.annotation.Reference#version
   * Reference.version}
   */
  private String version = "";

  /**
   * dubbo group, may override by {@link com.alibaba.dubbo.config.annotation.Service#group
   * Service.group} or {@link com.alibaba.dubbo.config.annotation.Reference#group Reference.group}
   */
  private String group = "";

  /**
   * dubbo protocols, use for much protocols
   */
  private Map<String,ProtocolConfig> protocols = new HashMap<String,ProtocolConfig>();
  
  
  public Map<String, ProtocolConfig> getProtocols() {
	return protocols;
}

public void setProtocols(Map<String, ProtocolConfig> protocols) {
	this.protocols = protocols;
}

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

  @Override
  public String toString() {
    return "DubboProperties [appname=" + this.appname + ", registry=" + this.registry + ", monitor="
        + this.monitor + ", protocol=" + this.protocol + ", port=" + this.port + ", threads="
        + this.threads + ", version=" + this.version + ", group=" + this.group + "]";
  }
}
