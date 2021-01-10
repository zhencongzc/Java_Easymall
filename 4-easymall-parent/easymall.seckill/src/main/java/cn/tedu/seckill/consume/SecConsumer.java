package cn.tedu.seckill.consume;

import cn.tedu.seckill.mapper.SecMapper;
import common.pojo.Success;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SecConsumer {
    @Autowired(required = false)
    private SecMapper secMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //秒杀消费端逻辑代码
    @RabbitListener(queues = "seckill-queue")
    public void consume(String msg) {
        /*
            1 解析消息 userPhone seckillId
            2 利用seckillId 减库存
                2.1 减成功没有秒杀完毕,成功记录
                2.2 减不成功说明秒杀完毕,不成
         */
        Long userPhone = Long.parseLong(msg.split("_")[0]);
        Long seckillId = Long.parseLong(msg.split("_")[1]);
        //减库存条件有限制 number>0 now<endTime now>startTime seckillId
        Date nowTime = new Date();
        //从redis验证减库存结果,合法,继续执行数据库,不合法,超卖了不执行后续
        Long decr = redisTemplate.opsForValue().decrement("num_" + seckillId);
        if (decr < 0) {
            //说明已经秒完了
            System.out.println("该商品已经秒杀完毕");
            return;
        }
        //affected (rows) 成功result=1 不成功result=0
        int result = secMapper.decreNumber(nowTime, seckillId);
        if (result == 0) {
            //减库存失败,当前用户秒杀失败卖完了
            System.out.println("当前用户" + userPhone + ",秒杀失败,卖完了");
            return;
        }
        //秒杀成功记录成功信息,写入数据库succes
        Success suc = new Success();
        suc.setCreateTime(nowTime);
        suc.setState(0);
        suc.setSeckillId(seckillId);
        suc.setUserPhone(userPhone);
        secMapper.insertSuccess(suc);
    }
}
