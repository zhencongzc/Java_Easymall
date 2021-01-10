package cn.tedu.user.service;

import common.pojo.User;

public interface UserService {
    boolean checkUserName(String userName);

    void doRegister(User user);

    String doLogin(User user);

    String getUserJson(String ticket);

    Boolean doLogout(String em_ticket);

}
