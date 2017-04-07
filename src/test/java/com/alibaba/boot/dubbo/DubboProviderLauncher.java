package com.alibaba.boot.dubbo;

import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import com.alibaba.boot.dubbo.service.UserService;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.spring.ServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * spring boot默认使用tomcat8，所以需要jdk7以上版本
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDubboConfiguration
public class DubboProviderLauncher {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderLauncher.class, args);
    }

    @Value(value = "${server.port}")
    private int port;

    @Bean
    public ServiceBean<UserService> userServiceServiceBean(@Autowired UserService userService) {
        ServiceBean<UserService> serviceBean = new ServiceBean<UserService>();
        serviceBean.setInterface(UserService.class);
        serviceBean.setRef(userService);
        serviceBean.setProtocols(Arrays.asList(new ProtocolConfig("dubbo"), new ProtocolConfig("feign", port)));
        return serviceBean;
    }

//    @Bean
//    消费端，此种方式可以避免使用@Reference注解，保持与spring注解一致
//    public ReferenceBean<UserService> userService() {
//        ReferenceBean<UserService> bean = new ReferenceBean<UserService>();
//        bean.setInterface(UserService.class);
//        return bean;
//    }
}
