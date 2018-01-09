spring-boot-starter-dubbo
===================================

[English](https://github.com/alibaba/spring-boot-starter-dubbo/blob/master/README.md)

Spring Boot with dubbo support. dubbo是一个RPC框架。 

支持jdk版本为1.6或者1.6+

（在修改源码前，请导入googlestyle-java.xml以保证一致的代码格式）

### 如何发布dubbo服务
spring-boot-starter-dubb目前没有发布release计划，需要clone下来传到私服使用
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

### change list

* 将jdk版本升级到1.8

* 将dubbo版本升级到 2.6.0

* 引入resteasy 相关pom来支持dubbo restful协议

* 增加了支持dubbo restful协议的配置：com.alibaba.boot.dubbo.DubboRestContextAutoConfiguration

* 修改了com.alibaba.boot.dubbo.DubboProperties以便能管理更多的配置

* 增加了方法级注解com.alibaba.dubbo.config.annotation.ServiceMethod，以便starter能在方法粒度去控制rpc行为

* 调整了DubboConsumerAutoConfiguration 和 DubboProviderAutoConfiguration 的部分逻辑

### 关于跨语言

本次改动希望通过restful协议来实现跨语言，考虑到php等语言不方便zk，因此，引入了redis注册中心。

服务同时注册到zk和redis两个注册中心，java内部走zk，其他语言通过只读账号去读redis。

需要配置2套dubbo-admin，一套管zk，一套管redis。

### 运行demo

demo代码位于项目的src/test目录，运行它需要做以下准备

* 准备zookeeper和redis，配置host如下：

```
127.0.0.1  my.nexus.com  #你的maven仓库
127.0.0.1   registry.zookeeper.me #你的zk地址
127.0.0.1   registry.redis.me #你的redis地址
```

* 启动代码：

```java
// com.alibaba.boot.dubbo.demo.AppLauncher
```

### 参考文档

* dubbo 介绍: http://dubbo.io/
* spring-boot 介绍: http://projects.spring.io/spring-boot/
* spring-boot-starter-dubbo 参考: https://github.com/linux-china/spring-boot-dubbo
