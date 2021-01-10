package cn.tedu.controller;

import cn.tedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    public UserService userService;

    @RequestMapping("/user/query/point")
    public String queryPoint(String userId) {
        int points = userService.queryPoint(userId);
        String pointsJson = "{\"points\":\"" + points + "\"}";
        return pointsJson;
    }
}
