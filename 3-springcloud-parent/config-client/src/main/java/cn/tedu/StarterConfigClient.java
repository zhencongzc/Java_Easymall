package cn.tedu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StarterConfigClient {
    public static void main(String[] args) {
        SpringApplication.run(StarterConfigClient.class, args);
    }

    @Value("${name}")
    private String name;

    @RequestMapping("/name")
    public void getName() {
        System.out.println(name);
    }
}
