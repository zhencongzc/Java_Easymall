package cn.tedu.seckill.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send(String msg) {
        //send 关注发送消息属性
        MessageProperties properties = new MessageProperties();
        properties.setPriority(100);
        properties.setContentEncoding("utf-8");
        Message message = new Message(msg.getBytes(), properties);
        rabbitTemplate.send("", "fanoutqueue1",message);

        //convertAndSend 关注发送消息体
        rabbitTemplate.convertAndSend("","fanoutqueue1",msg);
        System.out.println("发送消息成功");
    }
}
