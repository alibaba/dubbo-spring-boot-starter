package com.alibaba.boot.dubbo.demo.consumer;

import java.util.Map;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.boot.dubbo.demo.api.DepartmentReq;
import com.alibaba.boot.dubbo.demo.api.NameReq;
import com.alibaba.boot.dubbo.demo.api.TestReq;
import com.alibaba.boot.dubbo.demo.api.TestRes;
import com.alibaba.boot.dubbo.demo.api.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @DubboConsumer(check = false)
    private TestService testService;

    @GetMapping(value = "/invokeVoid")
    public String invokeVoid() {
        TestRes res = testService.invokeVoid();
        return res.getContent();
    }

    @GetMapping(value = "/invokeSingleString")
    public String invokeSingleString(String single) {
        TestRes res = testService.invokeSingleString(single);
        return res.getContent();
    }

    @GetMapping(value = "/invokeSingleInteger")
    public String invokeSingleInteger(Integer num) {
        TestRes res = testService.invokeSingleInteger(num);
        return res.getContent();
    }

    @GetMapping(value = "/invokeSingleInt")
    public String invokeSingleInt(int num) {
        TestRes res = testService.invokeSingleInt(num);
        return res.getContent();
    }

    @GetMapping(value = "/invokeSingleFloat")
    public String invokeSingleFloat(Float num) {
        TestRes res = testService.invokeSingleFloat(num);
        return res.getContent();
    }

    @GetMapping(value = "/invokeSingleFlo")
    public String invokeSingleFlo(float num) {
        TestRes res = testService.invokeSingleFlo(num);
        return res.getContent();
    }

    @GetMapping(value = "/invokeSingleBoolean")
    public String invokeSingleBoolean(Boolean bool) {
        TestRes res = testService.invokeSingleBoolean(bool);
        return res.getContent();
    }

    @GetMapping(value = "/invokeSingleBool")
    public String invokeSingleBool(boolean bool) {
        TestRes res = testService.invokeSingleBool(bool);
        return res.getContent();
    }

    @GetMapping(value = "/invokeArray")
    public String invokeArray(String[] values) {
        TestRes res = testService.invokeArray(values);
        return res.getContent();
    }

    @GetMapping(value = "/invokeObject")
    public String invokeObject(TestReq req) {
        TestRes res = testService.invokeObject(req);
        return res.getContent();
    }

    @GetMapping(value = "/invokeObjects")
    public String invokeObjects(TestReq req, NameReq nameReq, DepartmentReq departmentReq) {
        TestRes res = testService.invokeObjects(req, nameReq, departmentReq);
        return res.getContent();
    }

    @GetMapping(value = "/invokeMap")
    public String invokeMap(Map<String, String> map) {
        TestRes res = testService.invokeMap(map);
        return res.getContent();
    }

}
