package com.alibaba.dubbo.spring.boot.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Unique class.
 *
 * @author xionghui
 * @version 1.0.0
 * @see #equals(Object)
 * @see #hashCode()
 * @since 1.0.0
 */
@AllArgsConstructor
public class ClassIdBean implements Serializable {

  private static final long serialVersionUID = -6632632504039058978L;

  @Getter
  private final Class<?> interfaceClass;
  @Getter
  private final String group;
  @Getter
  private final String version;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ClassIdBean)) {
      return false;
    }
    ClassIdBean classIdBean = (ClassIdBean) obj;
    if (this.interfaceClass == null ? classIdBean.interfaceClass != null
        : !this.interfaceClass.equals(classIdBean.interfaceClass)) {
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
    hashCode = 31 * hashCode + (this.interfaceClass == null ? 0 : this.interfaceClass.hashCode());
    hashCode = 31 * hashCode + (this.group == null ? 0 : this.group.hashCode());
    hashCode = 31 * hashCode + (this.version == null ? 0 : this.version.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "ClassIdBean [interfaceClass=" + this.interfaceClass + ", group=" + this.group
        + ", version=" + this.version + "]";
  }
}
