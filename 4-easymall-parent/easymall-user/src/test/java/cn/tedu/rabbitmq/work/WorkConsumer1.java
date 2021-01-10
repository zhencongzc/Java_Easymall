package cn.tedu.rabbitmq.work;

import cn.tedu.rabbitmq.ChannelUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.Date;

/**
 * 1对1监听,获取消息
 */
public class WorkConsumer1 {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getInstance();
        //消费获取一条消息的封装对象
        DeliverCallback deliverCallback = (s, message) -> {
            byte[] body = message.getBody();
            System.out.println("消费端1监听到消息:" + new String(body));
        };
        CancelCallback cancelCallback = consumerTag -> System.out.println(consumerTag);
        channel.basicConsume("work-q", true, deliverCallback, cancelCallback);
    }
}
