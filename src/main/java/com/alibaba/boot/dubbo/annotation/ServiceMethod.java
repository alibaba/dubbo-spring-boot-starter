package com.alibaba.boot.dubbo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来控制服务提供方的方法配置
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface ServiceMethod {

    /**
     * 超时时间（单位是毫秒）
     * 可选
     *
     * @return 超时时间
     */
    int timeout() default 0;

    /**
     * 重试次数
     * 可选
     *
     * @return 重试次数
     */
    int retries() default 0;

    /**
     * 负载均衡策略
     * 可选
     *
     * @return 负载均衡策略
     */
    String loadbalance() default "";

    /**
     * 每服务消费者最大并发调用限制
     * 可选
     * @return 每服务消费者最大并发调用限制
     */
    int actives() default 0;

    /**
     * 是否启用JSR303标准注解验证，如果启用，将对方法参数上的参数进行校验
     *
     * @return 是否校验：true表示校验，false和其他表示不校验
     */
    String validation() default "";
}
