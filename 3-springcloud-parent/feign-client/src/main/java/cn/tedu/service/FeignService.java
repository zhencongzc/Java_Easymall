package cn.tedu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 申明式注解
 */

@FeignClient("service-hi")//当前抽象方法所有内容都会调用service-hi
public interface FeignService {
    //springmvc注解，映射具体的方法
    @RequestMapping(value = "/client/hello", method = RequestMethod.GET)
    String sayHi(@RequestParam("name") String name);
}
