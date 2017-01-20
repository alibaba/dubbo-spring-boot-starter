package com.alibaba.boot.dubbo.listener;

import com.alibaba.boot.dubbo.domain.ClassIdBean;
import com.alibaba.boot.dubbo.domain.SpringBootStarterDobboConstants;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * consumer invoke statics filter
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
@Activate(group = Constants.CONSUMER)
public class ConsumerInvokeStaticsFilter extends StaticsFilterHelper {

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    Invoker<?> invocationInvoker = invocation.getInvoker();
    Class<?> anInterface = invocationInvoker.getInterface();
    URL url = invocationInvoker.getUrl();
    String group = url.getParameter(SpringBootStarterDobboConstants.GROUP);
    String version = url.getParameter(SpringBootStarterDobboConstants.VERSION);
    ClassIdBean classIdBean = new ClassIdBean(anInterface, group, version);
    increase(classIdBean, invocation.getMethodName());
    return invoker.invoke(invocation);
  }
}

