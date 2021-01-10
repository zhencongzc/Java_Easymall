package cn.tedu.mapper;

import cn.tedu.bean.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    Order selectOrderById(@Param("orderId") String orderId);
}
