package cn.tedu.rabbitmq.simple;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import cn.tedu.rabbitmq.ChannelUtils;

import java.io.IOException;

/**
 * 1对1监听,获取消息
 */
public class SimpleConsumer {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getInstance();
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                System.out.println(s);
                byte[] body = delivery.getBody();
                System.out.println("传递的消息为：" + new String(body));
            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
                System.out.println("删除" + s);
            }
        };
        channel.basicConsume("simple-q", true, deliverCallback, cancelCallback);
//        channel.queueDelete("simple-q");
    }
}
