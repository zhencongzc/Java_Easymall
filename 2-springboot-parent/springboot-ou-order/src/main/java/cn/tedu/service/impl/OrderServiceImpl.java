package cn.tedu.service.impl;

import cn.tedu.bean.Order;
import cn.tedu.mapper.OrderMapper;
import cn.tedu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired(required = false)
    public OrderMapper orderMapper;

    @Autowired
    RestTemplate restTemplate;

    public void payOrder(String orderId) {
        //查找到订单
        Order order = orderMapper.selectOrderById(orderId);
        String userId = order.getUserId();
        Double money = order.getOrderMoney();
        //更新积分
        int points = money.intValue() * 10;
//        String url = "http://www.ou2.com/user/update/point?userId={1}&money={2}";
        String url = "http://user-server/user/update/point?userId={1}&money={2}";
        restTemplate.getForObject(url, Integer.class, userId, money);
    }
}
