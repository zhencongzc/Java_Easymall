package cn.tedu.controller;

import cn.tedu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    public OrderService orderService;

    @RequestMapping("/order/pay")
    public Integer payOrder(String orderId) {
        try {
            orderService.payOrder(orderId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
