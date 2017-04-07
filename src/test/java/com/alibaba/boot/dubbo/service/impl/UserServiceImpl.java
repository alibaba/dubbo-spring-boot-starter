package com.alibaba.boot.dubbo.service.impl;

import com.alibaba.boot.dubbo.model.User;
import com.alibaba.boot.dubbo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wuyu on 2017/4/7.
 */
@RestController
@RequestMapping(value = "/user")
public class UserServiceImpl implements UserService {

    private List<User> users = Arrays.asList(new User(1, "zhangsan", "123456"), new User(2, "lisi", "123456"));

    @Override
    public User findOne(Integer id) {
        for (User user : users) {
            if (user.getId().intValue() == user.getId().intValue()) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        for (User user : users) {
            if (user.getId().intValue() == user.getId().intValue()) {
                users.remove(user);
                break;
            }
        }
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
        for (User oldUser : users) {
            if (user.getId().intValue() == user.getId().intValue()) {
                users.remove(oldUser);
            }
        }
        users.add(user);
    }


    @Override
    public List<User> findAll() {
        return users;
    }

}
