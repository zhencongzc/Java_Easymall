package cn.tedu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControlller {
    @Value("${server.port}")
    public String port;

    @RequestMapping("/client/hello")
    public String sayHi(String name) {
        return "hello i am " + name + " from " + port;
    }
}
