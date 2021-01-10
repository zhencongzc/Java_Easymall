package cn.tedu.rabbitmq.work;

import cn.tedu.rabbitmq.ChannelUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class WorkDeclare {
    public static void main(String[] args) throws IOException {
        //拿到一个连接
        Channel channel = ChannelUtils.getInstance();
        channel.queueDeclare("work-q",false,false,false,null);
        System.out.println("声明队列完成");
    }
}
