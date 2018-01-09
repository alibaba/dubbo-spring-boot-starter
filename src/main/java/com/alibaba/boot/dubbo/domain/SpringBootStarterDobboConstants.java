package com.alibaba.boot.dubbo.domain;

/**
 * 提供一组常量，避免注解配置里直接写字符串
 * 直接写，容易写错（没法对书写内容做编译检查）
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
public class SpringBootStarterDobboConstants {

  public static final String GROUP = "group";

  public static final String VERSION = "version";

  /**
   * 布尔值true，dubbo为了扩展方便，用String类型类接收boolean数据
   */
  public static final String TRUE = "true";

  /**
   * 布尔值false，dubbo为了扩展方便，用String类型类接收boolean数据
   */
  public static final String FALSE = "false";

  /**
   * 协议名称：dubbo
   */
  public static final String PROTOCOL_DUBBO = "dubbo";

  /**
   * 协议名称：rest
   */
  public static final String PROTOCOL_REST = "rest";

  /**
   * 日志输出器名称：sfl4j
   */
  public static final String LOGGER_SFL4J = "sfl4j";

  /**
   * 日志输出器名称：log4j
   */
  public static final String LOGGER_LOG4j = "log4j";

  /**
   * 负载均衡策略（随机）
   */
  public static final String LOAD_BALANCE_RANDOM = "random";

  /**
   * 负载均衡策略（轮循）
   */
  public static final String LOAD_BALANCE_ROUND_ROBIN = "roundrobin";

  /**
   * 集群容错策略：快速失败，只发起一次调用，失败立即保错，通常用于非幂等性操作
   */
  public static final String LOAD_BALANCE_LEAST_ACTIVE = "leastactive";

  /**
   * 集群容错策略：失败转移，当出现失败，重试其它服务器，通常用于读操作，但重试会带来更长延迟
   */
  public static final String CLUSTER_FAIL_OVER = "failover";

  /**
   * 负载均衡策略（随机）
   */
  public static final String CLUSTER_FAIL_FAST = "failfast";

  /**
   * 集群容错策略：失败安全，出现异常时，直接忽略，通常用于写入审计日志等操作
   */
  public static final String CLUSTER_FAIL_SAFE = "failsafe";

  /**
   * 集群容错策略：失败自动恢复， 对于invoker调用失败， 后台记录失败请求，任务定时重发, 通常用于通知
   */
  public static final String CLUSTER_FAIL_BACK = "failback";

  /**
   * 集群容错策略：并行调用，只要一个成功即返回，通常用于实时性要求较高的操作，但需要浪费更多服务资源
   */
  public static final String CLUSTER_FORKING = "forking";
}
