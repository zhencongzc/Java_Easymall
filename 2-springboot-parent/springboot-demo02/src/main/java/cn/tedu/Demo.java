package cn.tedu;

import cn.tedu.bean.Bean2;
import cn.tedu.config.MyConfig1;
import cn.tedu.bean.Bean1;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
    //加载配置类
    @Test
    public void test01() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig1.class);
        context.getBean(Bean1.class);
        context.getBean(Bean2.class);
    }
}
