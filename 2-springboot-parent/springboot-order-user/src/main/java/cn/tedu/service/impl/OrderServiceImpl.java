package cn.tedu.service.impl;

import cn.tedu.bean.Order;
import cn.tedu.mapper.OrderMapper;
import cn.tedu.mapper.UserMapper;
import cn.tedu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    public OrderMapper orderMapper;

    @Autowired
    public UserMapper userMapper;

    public void payOrder(String orderId) {
        //查找到订单
        Order order = orderMapper.selectOrderById(orderId);
        String userId = order.getUserId();
        Double money = order.getOrderMoney();
        //更新积分
        int points = money.intValue() * 2;
        userMapper.updatePointByUserId(userId, points);
    }
}
