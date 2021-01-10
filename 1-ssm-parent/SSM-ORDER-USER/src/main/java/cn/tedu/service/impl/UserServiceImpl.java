package cn.tedu.service.impl;

import cn.tedu.mapper.UserMapper;
import cn.tedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserMapper userMapper;

    public int queryPoint(String userId) {
        return userMapper.selectPointsByUserId(userId);
    }
}
