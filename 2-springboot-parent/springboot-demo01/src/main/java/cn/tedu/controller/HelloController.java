package cn.tedu.controller;

import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class HelloController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/hello")
    public String sayHi() {
        return "hello i am from " + port;
    }

    @Autowired(required = false)
    public UserMapper userMapper;

    @RequestMapping("/points")
    public Integer queryPoint(String userId) {
        return userMapper.selectPointsByUserId(userId);
    }

    //写入session数据
    @RequestMapping("/write")
    public String save(String value, HttpSession session) {
        session.setAttribute("name", value);
        return "写入session属性值成功";
    }

    //读取session数据
    @RequestMapping("/read")
    public String get(HttpSession session) {
        String value = (String) session.getAttribute("name");
        return value;
    }
}
