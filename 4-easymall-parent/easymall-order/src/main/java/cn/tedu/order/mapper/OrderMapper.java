package cn.tedu.order.mapper;

import common.pojo.Order;

import java.util.List;

public interface OrderMapper {
    void insertOrder(Order order);

    List<Order> selectOrderByUserid(String userId);

    void delectOrderByOrderid(String orderId);
}
