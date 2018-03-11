package com.alibaba.dubbo.spring.boot.testcase;

import org.junit.Test;

import com.alibaba.dubbo.spring.boot.bean.ClassIdBean;

import junit.framework.TestCase;

public class ClassIdBeanTest extends TestCase {

  @Test
  public void testClassIdBeanNull() throws Exception {
    ClassIdBean classIdBean = new ClassIdBean(null, null, null);
    ClassIdBean anoClassIdBean = new ClassIdBean(null, null, null);
    assert (classIdBean.equals(anoClassIdBean));
    System.out.println("testClassIdBeanNull ok!");
  }

  @Test
  public void testClassIdBeanNotNull() throws Exception {
    ClassIdBean classIdBean = new ClassIdBean(null, "test", null);
    ClassIdBean anoClassIdBean = new ClassIdBean(null, "test", null);
    assert (classIdBean.equals(anoClassIdBean));
    System.out.println("testClassIdBeanNotNull ok!");
  }
}
