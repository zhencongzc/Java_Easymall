package cn.tedu.seckill.service;

import cn.tedu.seckill.mapper.SecMapper;
import common.pojo.Seckill;
import common.vo.SysResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SecService {
    @Autowired(required = false)
    private SecMapper secMapper;

    public List<Seckill> list() {
        return secMapper.selectSeckills();
    }

    public Seckill detail(Long seckillId) {
        return secMapper.selectSeckillById(seckillId);
    }

}
