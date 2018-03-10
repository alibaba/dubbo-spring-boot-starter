package com.alibaba.dubbo.spring.boot;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Dubbo properties
 *
 * @author xionghui
 * @author 韩旺坤
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
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


  @Override
  public String toString() {
    return "DubboProperties [appname=" + this.appname + ", server=" + this.server + ", registry="
        + this.registry + ", monitor=" + this.monitor + ", protocol=" + this.protocol + ", port="
        + this.port + ", threads=" + this.threads + ", version=" + this.version + ", group="
        + this.group + ", protocols=" + this.protocols + ", provider=" + this.provider
        + ", consumer=" + this.consumer + "]";
  }
}
