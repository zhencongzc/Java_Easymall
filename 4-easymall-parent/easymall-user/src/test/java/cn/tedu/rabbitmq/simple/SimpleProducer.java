package cn.tedu.rabbitmq.simple;

import cn.tedu.rabbitmq.ChannelUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class SimpleProducer {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getInstance();
        String msg = "大家好22123";
        channel.basicPublish("", "simple-q", null, msg.getBytes());
        /*
            exchange:String 表示消息发送到的交换机名字 (AMQP default),任何一个队列都会绑定
            这个交换机,路由模式,队列绑定路由key就是队列名字
            routingKey:队列绑定交换机的路由key
            props:BasicProperties 表示消息携带属性,比如发送消息appId 发送消息的userId,发送消息时字符串编解码,发送消息的集群id..
            目的让消费端获取属性值从而丰富消费逻辑,比如appId代表手机 代表电脑
            body:byte[]的消息二进制
         */
        System.out.println("生产端发送成功");
    }
}
