dubbo-spring-boot-starter [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.alibaba.spring.boot/dubbo-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alibaba.spring.boot/dubbo-spring-boot-starter)
===================================

[中文版文档](https://github.com/alibaba/dubbo-spring-boot-starter/blob/master/README_zh.md)

Dubbo Spring Boot Starter. Dubbo official [dubbo-spring-boot-project](https://github.com/dubbo/dubbo-spring-boot-project)

Support jdk version 1.6 or 1.6+

(please import googlestyle-java.xml if you want to modify the code)

### How to publish dubbo

* add Dependencies:

```xml
    <dependency>
        <groupId>com.alibaba.spring.boot</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
        <version>2.0.0</version>
    </dependency>
```
* add dubbo configuration in application.properties, demo:

```properties
spring.application.name=dubbo-spring-boot-starter
spring.dubbo.server=true
spring.dubbo.registry=N/A
```

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
        <version>2.0.0</version>
    </dependency>
```

* add dubbo configuration in application.properties, demo:

```properties
spring.application.name=dubbo-spring-boot-starter
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
  @Reference(url = "dubbo://127.0.0.1:20880")
  private IHelloService iHelloService;

}
```

### Reference

* dubbo: http://dubbo.io
* spring-boot: http://projects.spring.io/spring-boot
* dubbo-spring-boot-project: https://github.com/dubbo/dubbo-spring-boot-project
