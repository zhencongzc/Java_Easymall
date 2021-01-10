package cn.tedu.rabbitmq.work;

import cn.tedu.rabbitmq.ChannelUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * 1对1监听,获取消息
 */
public class WorkConsumer2 {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getInstance();
        //消费获取一条消息的封装对象
        DeliverCallback deliverCallback = (s, message) -> {
            //System.out.println(consumerTag);
            byte[] body = message.getBody();
            System.out.println("消费端2监听到消息:" + new String(body));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        CancelCallback cancelCallback = consumerTag -> System.out.println(consumerTag);
        channel.basicConsume("work-q", false, deliverCallback, cancelCallback);
    }
}
