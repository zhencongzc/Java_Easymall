package cn.tedu.product.controller;

import cn.tedu.product.service.IndexService;
import common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;

    @RequestMapping("/index/create")
    public SysResult indexCreate() {
        try {
            indexService.indexCreate();
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "创建索引失败", null);
        }
    }
}
