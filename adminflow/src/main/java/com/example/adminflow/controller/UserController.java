package com.example.adminflow.controller;


import com.example.adminflow.model.User;
import com.example.adminflow.permission.CheckPermission;
import com.example.adminflow.service.UserService;
import com.example.adminflow.util.CountResult;
import com.example.adminflow.util.DateUtil;
import com.example.adminflow.util.JwtToken;
import com.example.adminflow.util.StatusUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
public class UserController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "UserService")
    private UserService userService;

    // 登录认证的方法
    @GetMapping("/login")
    public StatusUtil login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("remember") String remember, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        User u = userService.getUserByName(username);
        boolean rem = Boolean.parseBoolean(remember);
        if (u != null && password.equals(u.getPassword())) {
            String format = DateUtil.format(new Date());
            // 每次用户登录成功，访问次数加一
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            String s = hashOps.get("loginCount", format);
            // 如果s不为null,就将起初的值先转为long，然后加一
            long count = s != null ? Long.parseLong(s) + 1 : 1;
            hashOps.put("loginCount", format, Long.toString(count));
            u.setPassword(null);
            // 将token信息存入到redis中
            String token = JwtToken.generateToken(u);
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            // 根据当前登录成功用户不同将用户信息变成token存储到redis中
            // 因为用户id唯一
            String token_name = u.getId() + "token";
            if (operations.get(token_name) == null) {
                operations.set(token_name, token, 60 * 60 * 24, TimeUnit.MINUTES);
            } else {
                return new StatusUtil("当前已存在登录用户，请稍后再试",500,null);
            }
            // 将用户信息生成token返回给前端
            Cookie c3 = new Cookie(u.getUsername()+"token", token);
            c3.setMaxAge(24 * 60 * 60);
            c3.setPath("/");
            c3.setDomain("localhost");
            response.addCookie(c3);
            if (rem) {
                Cookie c1 = new Cookie("username", username);
                c1.setMaxAge(24 * 60 * 60);
                c1.setPath("/");
                c1.setDomain("localhost");
                response.addCookie(c1);
                Cookie c2 = new Cookie("password", password);
                c2.setMaxAge(24 * 60 * 60);
                c2.setDomain("localhost");
                c2.setPath("/");
                response.addCookie(c2);
            } else {
                Cookie cookie1 = new Cookie("username", username);
                cookie1.setMaxAge(0);
                response.addCookie(cookie1);
                Cookie cookie2 = new Cookie("password", password);
                cookie2.setMaxAge(0);
                response.addCookie(cookie2);
            }
            // 将token放入到redis中
            redisTemplate.opsForValue().set(u.getUsername() + "token", token);
            // 设置键的生命周期为24小时
            redisTemplate.expire(u.getUsername() + "token", 24, TimeUnit.HOURS);
            return new StatusUtil("登录成功", 200,null);
        } else {
            return new StatusUtil("用户名或密码错误", 401,null);
        }

    }

    @GetMapping("/getUser")
    public StatusUtil getUser(@RequestParam("username")String username){
        User user = userService.getUserByName(username);
        user.setPassword(null);
        return new StatusUtil(null,200,user);
    }

    @PostMapping("/updateUser")
    public StatusUtil updateUser(@RequestBody User u) {
//        StatusUtil statusUtil = userService.upUser(u);
//        if (statusUtil.getU() != null) {
//            return statusUtil;
//        }
        return new StatusUtil("网络出现异常，请稍后再试", 300,null);
    }

    @GetMapping("/loginOut")
    public StatusUtil loginOut(@RequestParam("id") int id) {
        // 删除redis里面存储的用户信息
        Boolean roleId = redisTemplate.delete("roleId_" + id);
        if (roleId) {
            return new StatusUtil("注销成功", 200,null);
        } else {
            return new StatusUtil("网络出现异常，请稍后再试", 300,null);
        }
    }

    /*
    该方法是获取最近五天项目访问人数
     */
    @GetMapping("/getCount")
    public CountResult getAccessCount() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        List<String> numbers = new ArrayList<>();
        List<Long> score = new ArrayList<>();
        // 获取指定范围的字段和对应的值
        List<String> rangeValues = new ArrayList<>();
        Set<String> keys = hashOps.keys("loginCount");
        // 将set集合里面的元素放入list集合
        rangeValues.addAll(keys);
        for (int i = rangeValues.size() - 5; i < rangeValues.size(); i++) {
            String s = rangeValues.get(i);
            numbers.add(s);
            String s1 = hashOps.get("loginCount", s);
            if (s1 != null) {
                long l = Long.parseLong(s1);
                score.add(l);
            }
        }
        return new CountResult(numbers, score);
    }

    @GetMapping("/hello")
    @CheckPermission(permission = "User")
    public String hello() {
        return "欢迎你";
    }

    @GetMapping("/per/emp")
    public String loin() {
        return "发送请求了";
    }
}
