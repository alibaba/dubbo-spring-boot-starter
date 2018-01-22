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
 * consumer invoke statics filter
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
@Activate(group = Constants.CONSUMER)
public class ConsumerInvokeStaticsFilter extends StaticsFilterHelper {

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    Invoker<?> invocationInvoker = invocation.getInvoker();
    Class<?> interfaceClass = invocationInvoker.getInterface();
    URL url = invocationInvoker.getUrl();
    String group = url.getParameter(DubboSpringBootStarterConstants.GROUP);
    String version = url.getParameter(DubboSpringBootStarterConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(interfaceClass, group, version);
    increase(classIdBean, invocation.getMethodName());
    return invoker.invoke(invocation);
  }
}

