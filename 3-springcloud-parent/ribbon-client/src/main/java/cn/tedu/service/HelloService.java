package cn.tedu.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public String sayHi(String name) {
        String url = "http://service-hi/client/hello?name={1}";
        return restTemplate.getForObject(url, String.class, name);
    }

    @HystrixCommand(fallbackMethod = "sayHi")
    public String error(String name) {
        String url = "http://service-hi/client/hello?name={1}";
        return restTemplate.getForObject(url, String.class, name);
    }
}
