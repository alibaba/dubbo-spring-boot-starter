package com.alibaba.dubbo.spring.boot.listener;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.InvokerListenerAdapter;
import com.alibaba.dubbo.spring.boot.domain.ClassIdBean;
import com.alibaba.dubbo.spring.boot.domain.DubboSpringBootStarterConstants;

/**
 * dubbo client invoker listener
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Activate
public class ConsumerSubscribeListener extends InvokerListenerAdapter {
  // subscribe interfaces
  public static final Set<ClassIdBean> SUBSCRIBEDINTERFACES_SET =
      new ConcurrentHashSet<ClassIdBean>();

  // connection interface name
  public static final Map<ClassIdBean, Set<String>> CONNECTION_MAP =
      new ConcurrentHashMap<ClassIdBean, Set<String>>();

  @Override
  public void referred(Invoker<?> invoker) throws RpcException {
    Class<?> interfaceClass = invoker.getInterface();
    URL url = invoker.getUrl();
    String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
    String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
    SUBSCRIBEDINTERFACES_SET.add(classIdBean);
    Set<String> connectionSet = CONNECTION_MAP.get(classIdBean);
    if (connectionSet == null) {
      connectionSet = new ConcurrentHashSet<String>();
      CONNECTION_MAP.put(classIdBean, connectionSet);
    }
    connectionSet.add(invoker.getUrl().toString());
  }

  @Override
  public void destroyed(Invoker<?> invoker) {
    Class<?> interfaceClass = invoker.getInterface();
    URL url = invoker.getUrl();
    String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
    String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
    SUBSCRIBEDINTERFACES_SET.remove(classIdBean);
    Set<String> connectionSet = CONNECTION_MAP.get(classIdBean);
    if (connectionSet != null) {
      connectionSet.remove(invoker.getUrl().toString());
    }
    if (connectionSet == null || connectionSet.size() == 0) {
      CONNECTION_MAP.remove(classIdBean);
    }
  }
}
