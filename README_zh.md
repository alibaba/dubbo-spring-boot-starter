spring-boot-starter-dubbo
===================================

[English](https://github.com/alibaba/spring-boot-starter-dubbo/blob/master/README.md)

Spring Boot with dubbo support. dubbo是一个RPC框架。 

支持jdk版本为1.6或者1.6+

（在修改源码前，请导入googlestyle-java.xml以保证一致的代码格式）

### 如何发布dubbo服务

* 添加依赖:

```xml
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>spring-boot-starter-dubbo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
```

* 在application.properties添加dubbo的相关配置信息,样例配置如下:

```properties
spring.dubbo.appname=spring-boot-starter-dubbo-provider-test
spring.dubbo.registry=multicast://224.0.0.0:1111
spring.dubbo.protocol=dubbo
```

* 接下来在Spring Boot Application的上添加`@EnableDubboConfiguration`, 表示要开启dubbo功能. (dubbo provider服务可以使用或者不使用web容器)

```java
@SpringBootApplication
@EnableDubboConfiguration
public class DubboProviderLauncher {
  //...
}
```

* 编写你的dubbo服务,只需要添加要发布的服务实现上添加`@Service`（import com.alibaba.dubbo.config.annotation.Service）注解 ,其中interfaceClass是要发布服务的接口.

```java
@Service(interfaceClass = IHelloService.class)
public class HelloServiceImpl implements IHelloService {
  //...
}
```

* 启动你的Spring Boot应用,观察控制台,可以看到dubbo启动相关信息.


### 如何消费Dubbo服务

* 添加依赖:

```xml
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>spring-boot-starter-dubbo</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
```

* 在application.properties添加dubbo的相关配置信息,样例配置如下:

```properties
spring.dubbo.appname=spring-boot-starter-dubbo-consumer-test
spring.dubbo.registry=multicast://224.0.0.0:1111
spring.dubbo.protocol=dubbo
```

* 开启`@EnableDubboConfiguration`

```java
@SpringBootApplication
@EnableDubboConfiguration
public class DubboConsumerLauncher {
  //...
}
```

* 通过`@DubboConsumer`注入需要使用的interface.

```java
@Component
public class HelloConsumer {
  @DubboConsumer
  private IHelloService iHelloService;
}
```

### 参考文档

* dubbo 介绍: http://dubbo.io/
* spring-boot 介绍: http://projects.spring.io/spring-boot/
* spring-boot-starter-dubbo 参考: https://github.com/linux-china/spring-boot-dubbo
