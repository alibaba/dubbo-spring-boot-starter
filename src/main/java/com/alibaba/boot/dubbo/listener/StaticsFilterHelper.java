package com.alibaba.boot.dubbo.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.boot.dubbo.domain.ClassIdBean;
import com.alibaba.dubbo.rpc.Filter;

/**
 * statics filter
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
public abstract class StaticsFilterHelper implements Filter {
  public static final Map<ClassIdBean, Map<String, AtomicLong>> STATICS_DATA_MAP =
      new ConcurrentHashMap<ClassIdBean, Map<String, AtomicLong>>();

  public static void increase(ClassIdBean classIdBean, String methodName) {
    Map<String, AtomicLong> methodCountMap = STATICS_DATA_MAP.get(classIdBean);
    if (methodCountMap == null) {
      synchronized (StaticsFilterHelper.class) {
        // double check
        methodCountMap = STATICS_DATA_MAP.get(classIdBean);
        if (methodCountMap == null) {
          methodCountMap = new ConcurrentHashMap<String, AtomicLong>();
          STATICS_DATA_MAP.put(classIdBean, methodCountMap);
        }
      }
    }
    AtomicLong count = methodCountMap.get(methodName);
    if (count == null) {
      synchronized (StaticsFilterHelper.class) {
        // double check
        count = methodCountMap.get(methodName);
        if (count == null) {
          count = new AtomicLong(0);
          methodCountMap.put(methodName, count);
        }
      }
    }
    count.incrementAndGet();
  }

  public static long getValue(ClassIdBean classIdBean, String methodName) {
    Map<String, AtomicLong> methodCountMap = STATICS_DATA_MAP.get(classIdBean);
    if (methodCountMap == null) {
      return 0;
    }
    AtomicLong count = methodCountMap.get(methodName);
    return count == null ? 0 : count.get();
  }
}

