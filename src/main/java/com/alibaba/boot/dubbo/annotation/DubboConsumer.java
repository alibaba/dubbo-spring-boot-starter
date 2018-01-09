package com.alibaba.boot.dubbo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * dubbo consumer
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DubboConsumer {

  // 版本
  String version() default "";

  // 远程调用超时时间(毫秒)
  int timeout() default 0;

  // 注册中心
  String registry() default "";

  // 服务分组
  String group() default "";

  // 客户端类型
  String client() default "";

  // 点对点直连服务提供地址
  String url() default "";

  String protocol() default "";

  // 检查服务提供者是否存在
  boolean check() default true;

  // lazy create connection
  boolean lazy() default false;

  // 重试次数
  int retries() default 0;

  // 最大并发调用
  int actives() default 0;

  // 负载均衡
  String loadbalance() default "";

  // 是否异步
  boolean async() default false;

  // 异步发送是否等待发送成功
  boolean sent() default false;
}
