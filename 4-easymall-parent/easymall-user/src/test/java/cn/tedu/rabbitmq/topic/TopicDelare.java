package cn.tedu.rabbitmq.topic;

import cn.tedu.rabbitmq.ChannelUtils;
import cn.tedu.rabbitmq.DeclareUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class TopicDelare {

    public static void main(String[] args) throws IOException {
        //拿到一个连接
        Channel channel = ChannelUtils.getInstance();
        //声明2个队列
        channel.queueDeclare(DeclareUtils.TOPIC_QUEUE1,false,false,false,null);
        channel.queueDeclare(DeclareUtils.TOPIC_QUEUE2,false,false,false,null);
        //声明交换机
        channel.exchangeDeclare(DeclareUtils.TOPIC_EXCHANGE,DeclareUtils.TOPIC_TYPE);
        //绑定
        channel.queueBind(DeclareUtils.TOPIC_QUEUE1,DeclareUtils.TOPIC_EXCHANGE,"中国.#");
        channel.queueBind(DeclareUtils.TOPIC_QUEUE2,DeclareUtils.TOPIC_EXCHANGE,"中国.上海.*");
        System.out.println("声明完成");
    }
}
