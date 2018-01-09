package com.alibaba.boot.dubbo.demo.api;

import java.io.Serializable;

public class NameReq implements Serializable {

    private static final long serialVersionUID = 1387352480928927126L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NameReq{" +
                "name='" + name + '\'' +
                '}';
    }
}
