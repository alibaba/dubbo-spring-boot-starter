package com.alibaba.boot.dubbo.service;

import com.alibaba.boot.dubbo.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by wuyu on 2017/4/7.
 */
@FeignClient(path = "/user")
public interface UserService {

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    User findOne(@PathVariable(value = "id") Integer id);

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    void delete(@PathVariable(value = "id") Integer id);

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    void save(@RequestBody User user);

    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@RequestBody User user);

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    List<User> findAll();

}
