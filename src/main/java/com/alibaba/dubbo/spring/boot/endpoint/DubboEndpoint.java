package com.alibaba.dubbo.spring.boot.endpoint;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.Version;
import com.alibaba.dubbo.spring.boot.DubboProperties;
import com.alibaba.dubbo.spring.boot.domain.ClassIdBean;
import com.alibaba.dubbo.spring.boot.listener.ConsumerSubscribeListener;
import com.alibaba.dubbo.spring.boot.listener.ProviderExportListener;
import com.alibaba.dubbo.spring.boot.listener.StaticsFilterHelper;
import com.alibaba.dubbo.spring.boot.metrics.DubboMetrics;

/**
 * dubbo endpoint for provider and subscriber
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class DubboEndpoint extends AbstractEndpoint<Map<String, Object>> {
  @Autowired
  private DubboProperties dubboProperties;

  @Autowired
  private DubboMetrics dubboMetrics;

  @Override
  public Map<String, Object> invoke() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("endpoint", this.buildEndpoint());
    result.put("metrics", this.getMetrics());
    result.put("config", this.getConfig());
    result.put("runtime", this.getRuntime());
    return result;
  }

  private Object buildEndpoint() {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("name", this.getName());
    result.put("version", this.getVersion());
    result.put("authors", this.getAuthors());
    result.put("docs", this.getDocs());
    result.put("scm", this.getScm());
    return result;
  }

  public DubboEndpoint() {
    super("dubbo");
  }

  public String getName() {
    return "dubbo";
  }

  public String getVersion() {
    return Version.getVersion();
  }

  public List<String> getAuthors() {
    return Collections.singletonList("xionghui <xionghui_coder@163.com>");
  }

  public String getDocs() {
    return "http://dubbo.io/books/dubbo-user-book-en";
  }

  public String getScm() {
    return "git@github.com:alibaba/dubbo-spring-boot-starter.git";
  }

  public DubboProperties getConfig() {
    return this.dubboProperties;
  }

  public Map<String, Object> getRuntime() {
    Map<String, Object> runtimeMap = new HashMap<String, Object>();

    runtimeMap.put("appname", this.dubboProperties.getAppname());
    runtimeMap.put("registry", this.dubboProperties.getRegistry());
    runtimeMap.put("protocol", this.dubboProperties.getProtocol());
    runtimeMap.put("port", this.dubboProperties.getPort());
    runtimeMap.put("threads", this.dubboProperties.getThreads());

    // published services
    Map<ClassIdBean, Map<String, Long>> publishedInterfaceList =
        new HashMap<ClassIdBean, Map<String, Long>>();
    Set<ClassIdBean> publishedInterfaceSet = ProviderExportListener.EXPORTEDINTERFACES_SET;
    for (ClassIdBean classIdBean : publishedInterfaceSet) {
      Class<?> interfaceClass = classIdBean.getInterfaceClass();
      String interfaceClassCanonicalName = interfaceClass.getCanonicalName();
      if (!interfaceClassCanonicalName.equals("void")) {
        Map<String, Long> methodNames = new HashMap<String, Long>();
        for (Method method : interfaceClass.getMethods()) {
          methodNames.put(method.getName(),
              StaticsFilterHelper.getValue(classIdBean, method.getName()));
        }
        publishedInterfaceList.put(classIdBean, methodNames);
      }
    }
    if (!publishedInterfaceList.isEmpty()) {
      runtimeMap.put("publishedInterfaces", publishedInterfaceList);
    }

    // subscribed services
    Set<ClassIdBean> subscribedInterfaceSet = ConsumerSubscribeListener.SUBSCRIBEDINTERFACES_SET;
    Map<ClassIdBean, Map<String, Long>> subscribedInterfaceList =
        new HashMap<ClassIdBean, Map<String, Long>>();
    for (ClassIdBean classIdBean : subscribedInterfaceSet) {
      Map<String, Long> methodNames = new HashMap<String, Long>();
      Class<?> interfaceClass = classIdBean.getInterfaceClass();
      for (Method method : interfaceClass.getMethods()) {
        methodNames.put(method.getName(),
            StaticsFilterHelper.getValue(classIdBean, method.getName()));
      }
      subscribedInterfaceList.put(classIdBean, methodNames);
    }
    if (!subscribedInterfaceList.isEmpty()) {
      runtimeMap.put("subscribedInterfaces", subscribedInterfaceList);
    }

    // consumer connections
    runtimeMap.put("connections", ConsumerSubscribeListener.CONNECTION_MAP);
    return runtimeMap;
  }

  public Map<String, Object> getMetrics() {
    Map<String, Object> metricsMap = new HashMap<String, Object>();
    Collection<Metric<?>> metrics = this.dubboMetrics.metrics();
    for (Metric<?> metric : metrics) {
      metricsMap.put(metric.getName(), metric.toString());
    }
    return metricsMap;
  }
}
