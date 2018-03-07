dubbo-spring-boot-starter
===================================

[中文版文档](https://github.com/alibaba/dubbo-spring-boot-starter/blob/master/README_zh.md)

Dubbo Spring Boot Starter.

Support jdk version 1.6 or 1.6+

(please import googlestyle-java.xml if you want to modify the code)

### How to publish dubbo

* add Dependencies:

```xml
    <dependency>
        <groupId>com.alibaba.spring.boot</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
```
* single protocol config: add dubbo configuration in application.properties, demo:

```properties
spring.dubbo.appname=dubbo-spring-boot-starter-provider-test
spring.dubbo.registry=multicast://224.0.0.0:1111
spring.dubbo.protocol=dubbo
```
* more protocol config：add dubbo configuration in application.properties, demo:

```properties
spring.dubbo.appname = UMP_Service
spring.dubbo.registry = zookeeper://172.16.20.136:2181?backup=172.16.20.136:2182,172.16.20.136:2183
spring.dubbo.group = UMP_Service
##dubbo 协议
spring.dubbo.protocols.dubbo.name=dubbo
spring.dubbo.protocols.dubbo.port=28081
spring.dubbo.protocols.dubbo.threads=200
## hessian协议
spring.dubbo.protocols.hessian.name=hessian
spring.dubbo.protocols.hessian.port=28082
spring.dubbo.protocols.hessian.threads=100
```

注：这个配置只针对服务提供端，消费端不用指定协议，它自己会根据服务端的地址信息去解析协议
* then add `@EnableDubboConfiguration` on Spring Boot Application, indicates that dubbo is enabled.(web or non-web application can use dubbo provider)

```java
@SpringBootApplication
@EnableDubboConfiguration
public class DubboProviderLauncher {
  //...
}
```

* code your dubbo service, add `@Service`(import com.alibaba.dubbo.config.annotation.Service) on your service class, and interfaceClass is the interface which will be published.

```java
@Service(interfaceClass = IHelloService.class)
@Component
public class HelloServiceImpl implements IHelloService {
  //...
}
```

* Start Spring Boot.


### How to consume Dubbo

* add Dependencies:

```xml
    <dependency>
        <groupId>com.alibaba.spring.boot</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
```

* add dubbo configuration in application.properties, demo:

```properties
spring.dubbo.appname=dubbo-spring-boot-starter-consumer-test
spring.dubbo.registry=multicast://224.0.0.0:1111
spring.dubbo.protocol=dubbo
```

* then add `@EnableDubboConfiguration` on Spring Boot Application

```java
@SpringBootApplication
@EnableDubboConfiguration
public class DubboConsumerLauncher {
  //...
}
```

* injection interface by the `@Reference` annotation.

```java
@Component
public class HelloConsumer {
  @Reference
  private IHelloService iHelloService;

}
```

### Reference

* dubbo: http://dubbo.io/
* spring-boot: http://projects.spring.io/spring-boot/
