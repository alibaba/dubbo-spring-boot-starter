package com.alibaba.boot.dubbo.demo.api;

import java.io.Serializable;

public class TestRes implements Serializable {

    private static final long serialVersionUID = 5942267842130734181L;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TestRes{" +
                "content='" + content + '\'' +
                '}';
    }
}
