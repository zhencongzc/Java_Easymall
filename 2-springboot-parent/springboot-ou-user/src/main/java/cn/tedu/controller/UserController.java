package cn.tedu.controller;

import cn.tedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    public UserService userService;

    @Value("${server.port}")
    int port;

    @RequestMapping("/user/query/point")
    public String queryPoint(String userId) {
        int points = userService.queryPoint(userId);
        String pointsJson = "{\"points\":\"" + points + "\"}";
        return pointsJson;
    }

    @RequestMapping("/user/update/point")
    public Integer updatePoint(String userId,Double money){
        try {
            userService.updatePoint(userId, money);
            System.out.println(port);
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
