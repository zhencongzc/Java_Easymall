package cn.tedu.service;

public interface UserService {
    int queryPoint(String userId);

    void updatePoint(String userId, Double money);
}
