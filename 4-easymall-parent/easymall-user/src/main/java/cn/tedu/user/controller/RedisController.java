package cn.tedu.user.controller;

import common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {
    @Autowired
    private StringRedisTemplate template;

    @RequestMapping("/cluster")
    public String demo(String key, String value) {
        System.out.println(template.hasKey(key));
        template.expire(key, 200, TimeUnit.SECONDS);
        template.getExpire(key, TimeUnit.SECONDS);
        template.delete(key);

        ValueOperations<String, String> opsForValue = template.opsForValue();
        opsForValue.set(key, value);
        System.out.println(opsForValue.get(key));
        template.opsForHash().put("user", "name", "xixi");
        return "success";
    }

    @RequestMapping("/sendCode")
    public SysResult sendCode(String phone) {
        /*
            1，判断是否锁住
            2，没锁，判断list数量，5分钟内超过3个加锁
            3，正常生成验证码
         */
        ValueOperations<String, String> opsForValue = template.opsForValue();
        ListOperations<String, String> opsForList = template.opsForList();
        String lockKey = phone + ".lock";
        if (template.hasKey(lockKey)) {
            return SysResult.build(201, "用户已被锁住", null);
        } else {
            long timeNow = new Date().getTime();
            String listKey = phone + ".list";
            String codeKey = phone + ".code";
            Long size = opsForList.size(listKey);
            if (size == 3) {
                long timeFirst = Long.parseLong(opsForList.rightPop(listKey));
                if (timeNow - timeFirst < 1000 * 60 * 5) {
                    opsForValue.set(lockKey, "", 10, TimeUnit.MINUTES);
                    template.delete(codeKey);
                    template.delete(listKey);
                    return SysResult.build(202, "已经超过5分钟3次", null);
                }
            }
            //正常验证
            String code = String.valueOf(Math.ceil(Math.random() * 9000 + 1000));
            opsForValue.set(codeKey, code, 30, TimeUnit.MINUTES);
            opsForList.leftPush(listKey, timeNow + "");
            return SysResult.ok();
        }
    }
}
