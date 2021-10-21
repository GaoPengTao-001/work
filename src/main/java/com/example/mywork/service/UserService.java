package com.example.mywork.service;

import com.example.mywork.entity.User;
import com.example.mywork.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    /**
     * 日志打印
     */
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User Sel(String id) {
        try {
            return userMapper.selectUser(id);
        } catch (Exception e) {
            log.error("UserService.Sel异常", e);
        }
        return null;
    }

}
