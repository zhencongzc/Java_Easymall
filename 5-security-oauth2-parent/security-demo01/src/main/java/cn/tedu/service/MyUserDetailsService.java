package cn.tedu.service;

import cn.tedu.domain.Permission;
import cn.tedu.mapper.PermissionMapper;
import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在认证时，该方法被调用，利用登陆时使用的username
        //查询一个security的用户对象UserDetails
        //进行password对比
        cn.tedu.domain.User user = userMapper.selectUserByUsername(username);
        List<Permission> permissions = permissionMapper.selectPermissionByUserId(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permission p : permissions) {
            GrantedAuthority authority = new SimpleGrantedAuthority(p.getAuthority());
            authorities.add(authority);
        }
        User userDetails = new User(username, user.getPassword(), authorities);
        return userDetails;
    }
}
