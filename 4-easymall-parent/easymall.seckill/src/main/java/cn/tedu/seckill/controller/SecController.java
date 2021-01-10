package cn.tedu.seckill.controller;

import cn.tedu.seckill.service.SecService;
import common.pojo.Seckill;
import common.vo.SysResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/seckill/manage")
public class SecController {
    @Autowired
    private SecService secService;
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //查询所有秒杀商品list
    @RequestMapping("/list")
    public List<Seckill> list() {
        return secService.list();
    }

    @RequestMapping("/detail")
    public Seckill detail(Long seckillId) {
        return secService.detail(seckillId);
    }

    //进入秒杀核心
    @RequestMapping("/{seckillId}")
    public SysResult seckill(@PathVariable Long seckillId) {
        try {
            //将消息封装 谁秒杀了什么
            int userPhone = new Random().nextInt(9999);
            String msg = userPhone + "_" + seckillId;
            //使用redis判断某个用户的秒杀次数 比如 每个用户秒杀同一个商品只能一次
            if (redisTemplate.hasKey(msg)) {
                //说明该用户秒杀过
                return SysResult.build(202, "不能秒杀多次", null);
            }
            template.convertAndSend("seckill-ex", "seckill", msg);
            redisTemplate.opsForValue().set(msg, "", 1, TimeUnit.DAYS);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "秒杀出现问题", null);
        }
    }

}
