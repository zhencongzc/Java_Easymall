package cn.tedu.controller;

import cn.tedu.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {
    @Autowired
    public DemoService demoService;

    @RequestMapping("/get/data")
    @ResponseBody
    public String getName(int id) {
        return demoService.getName(id);
    }
}
