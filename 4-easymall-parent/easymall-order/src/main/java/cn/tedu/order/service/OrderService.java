package cn.tedu.order.service;

import common.pojo.Order;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);

    List<Order> queryOrder(String userId);

    void delectOrder(String orderId);
}
