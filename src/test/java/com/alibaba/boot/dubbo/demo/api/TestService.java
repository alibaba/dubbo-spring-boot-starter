package com.alibaba.boot.dubbo.demo.api;

import java.util.Map;

public interface TestService {

    TestRes invokeVoid();

    TestRes invokeSingleString(String single);

    TestRes invokeSingleInteger(Integer num);

    TestRes invokeSingleInt(int num);

    TestRes invokeSingleFloat(Float num);

    TestRes invokeSingleFlo(float num);

    TestRes invokeSingleBoolean(Boolean bool);

    TestRes invokeSingleBool(boolean bool);

    TestRes invokeArray(String[] values);

    TestRes invokeObject(TestReq req);

    TestRes invokeObjects(TestReq req, NameReq nameReq, DepartmentReq departmentReq);

    TestRes invokeMap(Map<String, String> map);

    TestRes invokeStringAndArray(String single, String[] values);

    TestRes invokeObjectAndString(TestReq req, String single);

    TestRes invokeArrayAndObjectAndString(String[] values, TestReq req, String single);

    TestRes invokeMapAndObjectAndArrayAndString(Map<String, String> map, TestReq req, String[] values, String single);

}
