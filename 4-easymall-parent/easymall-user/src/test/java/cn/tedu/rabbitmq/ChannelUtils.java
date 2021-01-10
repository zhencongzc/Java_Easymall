package cn.tedu.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChannelUtils {
    public static Channel getInstance() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("10.42.168.46");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            Connection connection = factory.newConnection();
            return connection.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
