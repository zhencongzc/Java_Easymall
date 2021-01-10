package cn.tedu.rabbitmq.route;

import cn.tedu.rabbitmq.ChannelUtils;
import cn.tedu.rabbitmq.DeclareUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/*
消息发送端
 */
public class RouteProductor {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getInstance();
        channel.basicPublish(DeclareUtils.DIRECT_EXCHANGE, "北京", null, "北京你好".getBytes());
    }
}
