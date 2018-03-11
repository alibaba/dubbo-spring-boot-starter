package com.alibaba.dubbo.spring.boot.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.spring.boot.DubboConsumerAutoConfiguration;
import com.alibaba.dubbo.spring.boot.bean.ClassIdBean;
import com.alibaba.dubbo.spring.boot.listener.ConsumerSubscribeListener;

/**
 * Dubbo health indicator
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public class DubboHealthIndicator extends AbstractHealthIndicator {

  @Override
  public void doHealthCheck(Health.Builder builder) throws Exception {
    boolean up = true;
    for (ClassIdBean classIdBean : ConsumerSubscribeListener.SUBSCRIBEDINTERFACES_SET) {
      Object service = DubboConsumerAutoConfiguration.getDubboReference(classIdBean);
      EchoService echoService = (EchoService) service;
      if (echoService != null) {
        try {
          echoService.$echo("Hello");
          builder.withDetail(classIdBean.toString(), Status.UP.getCode());
        } catch (Throwable t) {
          up = false;
          builder.withDetail(classIdBean.toString(),
              Status.DOWN.getCode() + ", message: " + t.getMessage());
        }
      }
    }
    if (up) {
      builder.up();
    } else {
      builder.down();
    }
  }
}
