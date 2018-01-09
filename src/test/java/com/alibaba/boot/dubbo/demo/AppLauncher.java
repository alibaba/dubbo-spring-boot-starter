package com.alibaba.boot.dubbo.demo;


import com.alibaba.boot.dubbo.annotation.EnableDubboConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.system.EmbeddedServerPortFileWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
@EnableAutoConfiguration
@EnableDubboConfiguration
@ComponentScan(basePackageClasses = {AppLauncher.class})
public class AppLauncher {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");

        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(AppLauncher.class).run(args);
        context.addApplicationListener(new ApplicationPidFileWriter());
        context.addApplicationListener(new EmbeddedServerPortFileWriter());

        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
    }

}