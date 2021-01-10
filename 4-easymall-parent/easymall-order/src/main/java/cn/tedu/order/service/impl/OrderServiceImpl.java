package cn.tedu.order.service.impl;

import cn.tedu.order.mapper.OrderMapper;
import cn.tedu.order.service.OrderService;
import common.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired(required = false)
    private OrderMapper orderMapper;

    @Override
    public void addOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderPaystate(0);
        order.setOrderTime(new Date());
        orderMapper.insertOrder(order);
    }

    @Override
    public List<Order> queryOrder(String userId) {
        return orderMapper.selectOrderByUserid(userId);
    }

    @Override
    public void delectOrder(String orderId) {
        orderMapper.delectOrderByOrderid(orderId);
    }
}
