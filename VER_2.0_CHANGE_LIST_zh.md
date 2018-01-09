
# 2.0 版本 change list

* 将jdk版本升级到1.8

* 将dubbo版本升级到 2.6.0

* 引入resteasy 相关pom来支持dubbo restful协议

* 增加了支持dubbo restful协议的配置：com.alibaba.boot.dubbo.DubboRestContextAutoConfiguration

* 修改了com.alibaba.boot.dubbo.DubboProperties以便能管理更多的配置

* 增加了方法级注解com.alibaba.dubbo.config.annotation.ServiceMethod，以便starter能在方法粒度去控制rpc行为

* 调整了DubboConsumerAutoConfiguration 和 DubboProviderAutoConfiguration 的部分逻辑

## 关于跨语言

本次改动希望通过restful协议来实现跨语言，考虑到php等语言不方便zk，因此，引入了redis注册中心。

服务同时注册到zk和redis两个注册中心，java内部走zk，其他语言通过只读账号去读redis。

需要配置2套dubbo-admin，一套管zk，一套管redis。

## 运行demo

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