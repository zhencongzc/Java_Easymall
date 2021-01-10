package cn.tedu.user.service.impl;

import cn.tedu.user.mapper.UserMapper;
import cn.tedu.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.pojo.User;
import common.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate template;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean checkUserName(String userName) {
        int exist = userMapper.selectExistByUsername(userName);
        return exist == 0;
    }

    @Override
    public void doRegister(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        String md5Password = MD5Util.md5(user.getUserPassword());
        user.setUserPassword(md5Password);
        userMapper.insertUser(user);
    }

    @Override
    public String doLogin(User user) {
        /*
        1 验证用户名密码
        2 成功登录判断当前登录用户是否有前辈 login_userName
        3 如果有数据拿到前面的ticket 删除
        4 完成本次登录逻辑,同时在login_username中存储自己本次的ticket
        */
        ValueOperations<String, String> opsForValue = template.opsForValue();
        try {
            user.setUserPassword(MD5Util.md5(user.getUserPassword()));
            User existUser = userMapper.selectExistByUsernameAndPassword(user);
            if (existUser == null) {
                return "";
            } else {
                String loginKey = "login_" + user.getUserName();
                if (template.hasKey(loginKey)) {
                    String oldTicket = opsForValue.get(loginKey);
                    template.delete(oldTicket);
                }
                //新用户的key  EM_TICKET_admin1239317523589
                String key = "EM_TICKET_" + user.getUserName() + System.currentTimeMillis();
                existUser.setUserName(existUser.getUserNickname());
                existUser.setUserPassword(null);
                String userJson = mapper.writeValueAsString(existUser);
                opsForValue.set(key, userJson, 2, TimeUnit.HOURS);
                opsForValue.set(loginKey, key, 2, TimeUnit.HOURS);
                return key;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String getUserJson(String ticket) {
        //实现超时续租
        /*
            每次访问这个方法都证明用户操作网页,没有下线
            1 用ticket判断 userJson剩余时间
            2 如果剩余1小时以上,什么都不做,把userJson返回
            3 如果剩余时间小于1小时了,用户至少连续访问了1小时,延长1小时
         */
        //从ticket解析userName EM_TICKET_admin1597902200891
        String loginKey = "login_" + getUsername(ticket);
        Long lestTime = template.getExpire(ticket, TimeUnit.MINUTES);
        if (lestTime < 60) {
            template.expire(ticket, 2, TimeUnit.HOURS);
            template.expire(loginKey, 2, TimeUnit.HOURS);
        }
        return template.opsForValue().get(ticket);
    }

    @Override
    public Boolean doLogout(String em_ticket) {
        try {
            template.delete(em_ticket);
            String nameAndTime = em_ticket.split("_")[2];
            int length = nameAndTime.length();
            String userName = nameAndTime.substring(0, length - 13);
            String loginKey = "login_" + userName;
            template.delete(loginKey);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getUsername(String ticket) {
        String nameAndTime = ticket.split("_")[2];
        int length = nameAndTime.length();
        String userName = nameAndTime.substring(0, length - 13);
        return userName;
    }
}
