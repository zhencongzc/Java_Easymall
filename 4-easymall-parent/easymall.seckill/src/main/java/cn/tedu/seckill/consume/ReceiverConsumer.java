package cn.tedu.seckill.consume;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverConsumer {
//    @RabbitListener(queues = {"fanoutqueue1", "fanoutqueue2"})
//    public void consume(String msg) {
//        System.out.println(msg);
//    }
}
