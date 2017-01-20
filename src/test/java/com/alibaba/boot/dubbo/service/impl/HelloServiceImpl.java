package com.alibaba.boot.dubbo.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.boot.dubbo.service.IHelloService;
import com.alibaba.dubbo.config.annotation.Service;

@Service(interfaceClass = IHelloService.class)
@Component
public class HelloServiceImpl implements IHelloService {

  @Override
  public String hello() {
    return "hello";
  }
}
