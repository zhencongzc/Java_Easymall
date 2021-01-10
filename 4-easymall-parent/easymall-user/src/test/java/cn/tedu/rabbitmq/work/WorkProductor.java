package cn.tedu.rabbitmq.work;

import cn.tedu.rabbitmq.ChannelUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/*
消息发送端
 */
public class WorkProductor {
    public static void main(String[] args) throws IOException {
        Channel channel = ChannelUtils.getInstance();

        for (int i = 0; i < 100; i++) {
            String msg = "消息_" + i + "条";
            channel.basicPublish("", "work-q", null, msg.getBytes());
            System.out.println("生产端发送[SEND]成功第" + i + "个");
        }
    }
}
