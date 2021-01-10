package cn.tedu.rabbitmq.simple;

import cn.tedu.rabbitmq.ChannelUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public class SimpleDeclare {
    public static void main(String[] args) throws IOException {
        //拿到一个连接
        Channel channel = ChannelUtils.getInstance();
        //声明一个叫做simple-q的队列
        channel.queueDeclare("simple-q", false, false, false, null);
        /*声明方法特点:有则直接使用,无则创建
            1 queue:String 表示队列名称
            2 durable:Boolean 表示是否持久化
            3 exclusive:是否专属 如果专属,只有创建该队列的连接对象才能使用队列
            4 autoDelete:是否自动删除 从第一个监听队列的消费者算,直到最后一个消费端连接断开自动删除
            5 arg:Map<String,Object> 设置队列的属性,比如最大消息长度,最大消息容量,最大存活时间...
         */
        System.out.println("声明队列完成");
    }
}
