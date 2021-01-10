package cn.tedu.order.controller;

import cn.tedu.order.service.OrderService;
import common.pojo.Order;
import common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order/manage")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/save")
    public SysResult addOrder(Order order) {
        try {
            orderService.addOrder(order);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "新增订单失败", null);
        }
    }

    @RequestMapping("/query/{userId}")
    public List<Order> queryOrder(@PathVariable String userId) {
        return orderService.queryOrder(userId);
    }

    @RequestMapping("/delete/{orderId}")
    public SysResult delectOrder(@PathVariable String orderId) {
        try {
            orderService.delectOrder(orderId);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "删除订单失败", null);
        }
    }
}
