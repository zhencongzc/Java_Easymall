package cn.tedu.config;

import cn.tedu.bean.Bean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 当前配置类等价于一个xml配置文件
 */
@Configuration
public class MyConfig2 {
    public MyConfig2() {
        System.out.println("config2被容器加载了");
    }

}
