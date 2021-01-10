package cn.tedu.user.mapper;

import common.pojo.User;

public interface UserMapper {
    int selectExistByUsername(String userName);

    void insertUser(User user);

    User selectExistByUsernameAndPassword(User user);
}
