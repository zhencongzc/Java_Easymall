package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@SpringBootApplication
@RestController
public class StarterSession {
    public static void main(String[] args) {
        SpringApplication.run(StarterSession.class, args);
    }

    //写入session属性
    @RequestMapping("/set")
    public String set(String name, String value, HttpSession session) {
        session.setAttribute(name, value);
        return "success";
    }

    //读取session属性
    @RequestMapping("/get")
    public String get(String name, HttpSession session) {
        String value = (String) session.getAttribute(name);
        return value;
    }
}
