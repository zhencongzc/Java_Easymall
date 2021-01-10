package cn.tedu.user.controller;

import cn.tedu.user.service.UserService;
import common.pojo.User;
import common.utils.CookieUtils;
import common.vo.SysResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/manage")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/checkUserName")
    public SysResult checkUserName(String userName) {
        boolean available = userService.checkUserName(userName);
        if (available) {
            return SysResult.ok();
        } else {
            return SysResult.build(201, "不可用", null);
        }
    }

    @RequestMapping("/save")
    public SysResult doRegister(User user) {
        try {
            userService.doRegister(user);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "注册失败", null);
        }
    }

    @RequestMapping("login")
    public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        String ticket = userService.doLogin(user);
        if (StringUtils.isEmpty(ticket)) {
            return SysResult.build(201, "用户名或密码错误", null);
        } else {
            CookieUtils.setCookie(request, response, "EM_TICKET", ticket);
            return SysResult.ok();
        }
    }

    @RequestMapping("/query/{ticket}")
    public SysResult getUserJson(@PathVariable String ticket, HttpServletRequest request, HttpServletResponse response) {
        String userJson = userService.getUserJson(ticket);
        if (StringUtils.isEmpty(userJson)) {
            CookieUtils.deleteCookie(request, response, "EM_TICKET");
            return SysResult.build(201, "登录超时", null);
        } else {
            return SysResult.build(200, "登录成功", userJson);
        }
    }

    @RequestMapping("/logout")
    public SysResult doLogout(HttpServletRequest request, HttpServletResponse response) {
        String em_ticket = CookieUtils.getCookieValue(request, "EM_TICKET");
        CookieUtils.deleteCookie(request, response, "EM_TICKET");
        Boolean flag = userService.doLogout(em_ticket);
        if (flag) {
            return SysResult.build(200, "成功登出", null);
        } else {
            return SysResult.build(201, "登出失败", null);
        }
    }

}
