package com.alibaba.dubbo.spring.boot.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.spring.boot.DubboConsumerAutoConfiguration;
import com.alibaba.dubbo.spring.boot.domain.ClassIdBean;
import com.alibaba.dubbo.spring.boot.listener.ConsumerSubscribeListener;

/**
 * dubbo health indicator
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class DubboHealthIndicator extends AbstractHealthIndicator {

  @Override
  public void doHealthCheck(Health.Builder builder) throws Exception {
    for (ClassIdBean classIdBean : ConsumerSubscribeListener.SUBSCRIBEDINTERFACES_SET) {
      Object service = DubboConsumerAutoConfiguration.getDubboReference(classIdBean);
      EchoService echoService = (EchoService) service;
      if (echoService != null) {
        echoService.$echo("Hello");
        builder.withDetail(classIdBean.toString(), true);
      }
    }
    builder.up();
  }
}
