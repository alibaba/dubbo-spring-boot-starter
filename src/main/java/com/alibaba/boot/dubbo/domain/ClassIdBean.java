package com.alibaba.boot.dubbo.domain;

import java.io.Serializable;

/**
 * 标示唯一一个class
 *
 * @author xionghui
 * @email xionghui.xh@alibaba-inc.com
 * @since 1.0.0
 */
public class ClassIdBean implements Serializable {
  private static final long serialVersionUID = 3744915458507135546L;

  private Class<?> clazz;
  private String group;
  private String version;

  public ClassIdBean(Class<?> clazz, String group, String version) {
    this.clazz = clazz;
    this.group = group;
    this.version = version;
  }

  public Class<?> getClazz() {
    return this.clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  public String getGroup() {
    return this.group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ClassIdBean)) {
      return false;
    }
    ClassIdBean classIdBean = (ClassIdBean) obj;
    if (this.clazz == null ? classIdBean.clazz != null : !this.clazz.equals(classIdBean.clazz)) {
      return false;
    }
    if (this.group == null ? classIdBean.group != null : !this.group.equals(classIdBean.group)) {
      return false;
    }
    return this.version == null ? classIdBean.version == null
        : this.version.equals(classIdBean.version);
  }

  @Override
  public int hashCode() {
    int hashCode = 17;
    hashCode = 31 * hashCode + (this.clazz == null ? 0 : this.clazz.hashCode());
    hashCode = 31 * hashCode + (this.group == null ? 0 : this.group.hashCode());
    hashCode = 31 * hashCode + (this.version == null ? 0 : this.version.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ClassIdBean [clazz=" + this.clazz + ", group=" + this.group + ", version="
        + this.version + "]";
  }
}
