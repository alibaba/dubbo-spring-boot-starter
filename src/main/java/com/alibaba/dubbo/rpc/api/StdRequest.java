package com.alibaba.dubbo.rpc.api;

import java.io.Serializable;

public class StdRequest<T extends Serializable> implements Serializable {

    private String tid;

    private Long ts;

    private T data;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StdRequest{" +
                "tid='" + tid + '\'' +
                ", ts=" + ts +
                ", data=" + data +
                '}';
    }
}
