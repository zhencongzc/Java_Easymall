package cn.tedu.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.seckill.mapper")
public class StarterSeckill {
    public static void main(String[] args) {
        SpringApplication.run(StarterSeckill.class, args);
    }

    @Bean
    //声明队列 seckill-queue
    //channel.ququeDeclare
    public Queue queue01() {
        return new Queue("seckill-queue", true, false, false, null);
    }

    @Bean
    //声明交换机 DirectExchange FanoutExchange TopicExchange
    public DirectExchange ex01() {
        return new DirectExchange("seckill-ex");
    }

    //绑定2个组件
    @Bean
    public Binding bind01() {
        return BindingBuilder.bind(queue01()).to(ex01()).with("seckill");
    }
}
