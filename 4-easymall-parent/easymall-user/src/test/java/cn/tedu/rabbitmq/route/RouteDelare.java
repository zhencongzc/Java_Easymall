package cn.tedu.rabbitmq.route;

import cn.tedu.rabbitmq.ChannelUtils;
import cn.tedu.rabbitmq.DeclareUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class RouteDelare {

    public static void main(String[] args) throws IOException {
        //拿到一个连接
        Channel channel = ChannelUtils.getInstance();
        //声明2个队列
        channel.queueDeclare(DeclareUtils.DIRECT_QUEUE1, false, false, false, null);
        channel.queueDeclare(DeclareUtils.DIRECT_QUEUE2, false, false, false, null);
        //声明交换机
        channel.exchangeDeclare(DeclareUtils.DIRECT_EXCHANGE, DeclareUtils.DIRECT_TYPE);
        //绑定
        channel.queueBind(DeclareUtils.DIRECT_QUEUE1, DeclareUtils.DIRECT_EXCHANGE, "北京");
        channel.queueBind(DeclareUtils.DIRECT_QUEUE2, DeclareUtils.DIRECT_EXCHANGE, "上海");
        System.out.println("声明完成");
    }
}
