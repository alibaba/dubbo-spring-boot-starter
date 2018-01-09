package com.alibaba.dubbo.rpc.api;

import java.io.Serializable;

/**
 * 通用的请求Wapper，设计这个类的目的，是为了帮助开发规范传参
 * 特别是在异构语言通过restful协议调dubbo时候
 *
 * @param <T>
 */
public class StdRequest<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 5993917960432053675L;

    /**
     * 会话跟踪id，也就是traceId，由consumer侧生成并确保全局唯一
     * 每笔业务都用不同的traceId，同一笔业务多次重试用相同的traceId
     * 作用有2个：
     * 1.用来做会话跟踪，之所以放在数据体里，而没有放在dubbo附件中/http header，是为了让传参更加紧凑
     * 2.用来辅助provider实现幂等读 和 幂等写
     */
    private String tid;

    /**
     * 回话跟踪时间戳，也就是request timestamp，单位是毫秒，数据类型是int64，是consumer侧发送请求的时间点
     * 可以用来结合provider侧日志，辅助分析请求在网络链路、线程池队列中的耗时请求
     */
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
