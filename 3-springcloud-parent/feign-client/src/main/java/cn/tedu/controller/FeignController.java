package cn.tedu.controller;

import cn.tedu.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    private FeignService feignService;

    @RequestMapping("/feign/hello")
    public String sayHi(String name) {
        return "FEIGB:" + feignService.sayHi(name);
    }
}
