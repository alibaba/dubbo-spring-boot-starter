package com.alibaba.dubbo.spring.boot.listener;

import java.util.Set;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.ExporterListenerAdapter;
import com.alibaba.dubbo.spring.boot.domain.ClassIdBean;
import com.alibaba.dubbo.spring.boot.domain.DubboSpringBootStarterConstants;

/**
 * provider export listener
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Activate
public class ProviderExportListener extends ExporterListenerAdapter {
  // exported interfaces
  public static final Set<ClassIdBean> EXPORTEDINTERFACES_SET =
      new ConcurrentHashSet<ClassIdBean>();

  @Override
  public void exported(Exporter<?> exporter) throws RpcException {
    Invoker<?> invoker = exporter.getInvoker();
    Class<?> interfaceClass = invoker.getInterface();
    URL url = invoker.getUrl();
    String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
    String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
    EXPORTEDINTERFACES_SET.add(classIdBean);
  }

  @Override
  public void unexported(Exporter<?> exporter) {
    Invoker<?> invoker = exporter.getInvoker();
    Class<?> interfaceClass = invoker.getInterface();
    URL url = invoker.getUrl();
    String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
    String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
    EXPORTEDINTERFACES_SET.remove(classIdBean);
  }
}
