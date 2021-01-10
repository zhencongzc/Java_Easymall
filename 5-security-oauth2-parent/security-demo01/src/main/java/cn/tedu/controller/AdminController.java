package cn.tedu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @RequestMapping("/admin/write")
    public String write() {
        return "写入数据";
    }

    @RequestMapping("/admin/update")
    public String update() {
        return "更新数据";
    }

    @RequestMapping("/admin/delete")
    public String delete() {
        return "删除数据";
    }

    @RequestMapping("/user/read")
    public String read() {
        return "读取数据";
    }
}

