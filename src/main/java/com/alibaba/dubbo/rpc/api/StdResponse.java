package com.alibaba.dubbo.rpc.api;

import java.io.Serializable;

/**
 * 通用的响应warpper，用来统一响应的数据结构
 *
 * @param <T>
 */
public class StdResponse<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -1312794099046310410L;

    /**
     * 请求中的会话跟踪id通过响应回显
     */
    private String tid;

    /**
     * 响应时间点，单位是毫秒，单位是毫秒，数据类型是int64
     */
    private Long ts;

    private Integer code;

    private String message;

    private T data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StdResponse{" +
                "tid='" + tid + '\'' +
                ", ts=" + ts +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
