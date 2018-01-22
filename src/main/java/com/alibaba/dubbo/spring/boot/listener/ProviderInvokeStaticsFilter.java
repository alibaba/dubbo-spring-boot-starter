package com.alibaba.dubbo.spring.boot.listener;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.spring.boot.domain.ClassIdBean;
import com.alibaba.dubbo.spring.boot.domain.DubboSpringBootStarterConstants;

/**
 * provider invoke statics filter
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Activate(group = Constants.PROVIDER)
public class ProviderInvokeStaticsFilter extends StaticsFilterHelper {

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    Class<?> interfaceClass = invoker.getInterface();
    URL url = invoker.getUrl();
    String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
    String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
    increase(classIdBean, invocation.getMethodName());
    return invoker.invoke(invocation);
  }
}
