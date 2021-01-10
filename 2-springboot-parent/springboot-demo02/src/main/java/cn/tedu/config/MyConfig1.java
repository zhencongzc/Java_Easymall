package cn.tedu.config;

import cn.tedu.bean.Bean1;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 当前配置类等价于一个xml配置文件
 */
@Configuration
@ComponentScan(basePackages = {"cn.tedu.bean"})
@Import({MyConfig2.class})
public class MyConfig1 {
    public MyConfig1() {
        System.out.println("config1被容器加载了");
    }

    //创建容器管理对象
    //当配置类被加载,@Bean所在的方法就会被加载的MyConfig1调用
    //方法返回值,会交给容器ioc管理
    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }
}
