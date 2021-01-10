package common.pojo;

import java.util.Date;
import java.util.List;

public class Order {
	//t_order
	private String orderId;//order_id
	private Double orderMoney;//order_money
	private String orderReceiverinfo;//order_receiverinfo
	private Integer orderPaystate;//order_paystate
	private Date orderTime;//order_time
	private String userId;//user_id
	//对多的关联表格的相关属性
	//t_order_item 有几个元素,insert
	private List<OrderItem> orderItems;//体现一对多关系的属性
	//表示一个订单对应多个订单商品.
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getOrderReceiverinfo() {
		return orderReceiverinfo;
	}
	public void setOrderReceiverinfo(String orderReceiverinfo) {
		this.orderReceiverinfo = orderReceiverinfo;
	}
	public Integer getOrderPaystate() {
		return orderPaystate;
	}
	public void setOrderPaystate(Integer orderPaystate) {
		this.orderPaystate = orderPaystate;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
