package com.alibaba.boot.dubbo.demo.api;

import java.io.Serializable;

public class TestReq implements Serializable {

    private static final long serialVersionUID = 2963688063012652951L;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TestReq{" +
                "content='" + content + '\'' +
                '}';
    }
}
