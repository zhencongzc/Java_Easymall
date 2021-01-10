package cn.tedu.config;

import cn.tedu.encoder.MyPasswordEncoder;
import cn.tedu.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    //利用注入的repository,对redis的session实现增删拆改
    //创建一个session的redis注册表 存储map中 user对象和sessionId对应关系的
    @Autowired
    private FindByIndexNameSessionRepository redisRepository;

    @Bean
    public SpringSessionBackedSessionRegistry registry() {
        return new SpringSessionBackedSessionRegistry(redisRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }

    //构造一个Bean内存容器对象
    @Bean
    public UserDetailsService myUserDetailsService() {
        return new MyUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService());
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("123456")
//                .authorities("read");
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("123456")
//                .authorities("read", "write", "delete", "update");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/read").hasAuthority("read")
                .antMatchers("/admin/write").hasAuthority("write")
                .antMatchers("/admin/update").hasAuthority("update")
                .antMatchers("/admin/delete").hasAuthority("delete")
                .anyRequest().authenticated();
        //表单登陆认证
        http.formLogin();
        //会话并发
        http.sessionManagement().maximumSessions(1).sessionRegistry(registry());
    }
}
