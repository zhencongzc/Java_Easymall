package cn.tedu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @RequestMapping("/home")
    public String guardResource(){
        return "您访问到了被保护的资源";
    }
}
