package cn.tedu.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private String orderId;
    private double orderMoney;
    private String userId;
}
