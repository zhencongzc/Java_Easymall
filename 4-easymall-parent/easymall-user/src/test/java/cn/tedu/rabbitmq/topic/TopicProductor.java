package cn.tedu.rabbitmq.topic;

import cn.tedu.rabbitmq.ChannelUtils;
import cn.tedu.rabbitmq.DeclareUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/*
消息发送端
 */
public class TopicProductor {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getInstance();
        channel.basicPublish(DeclareUtils.TOPIC_EXCHANGE,"中国.北京.朝阳",null,"北京你好".getBytes());
    }
}
