package com.alibaba.boot.dubbo.demo.api;

import java.io.Serializable;

public class DepartmentReq implements Serializable {

    private static final long serialVersionUID = 1104199821455680223L;

    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "DepartmentReq{" +
                "department='" + department + '\'' +
                '}';
    }
}
