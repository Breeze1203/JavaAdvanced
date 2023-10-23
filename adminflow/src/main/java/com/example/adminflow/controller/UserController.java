package com.example.adminflow.controller;


import com.example.adminflow.model.User;
import com.example.adminflow.permission.CheckPermission;
import com.example.adminflow.service.RoleService;
import com.example.adminflow.service.UserService;
import com.example.adminflow.util.CountResult;
import com.example.adminflow.util.DateUtil;
import com.example.adminflow.util.StatusUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Resource(name = "UserService")
    private UserService userService;

    @Resource(name = "RoleService")
    RoleService roleService;


    // 登录认证的方法
    @GetMapping("/login")
    public StatusUtil login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("remember") String remember, HttpServletResponse response) {
        User u = userService.getUserByName(username);
        boolean rem = Boolean.parseBoolean(remember);
        if (u != null && password.equals(u.getPassword())) {
            String format = DateUtil.format(new Date());
            // 每次用户登录成功，访问次数加一
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            String s = hashOps.get("loginCount", format);
            // 如果s不为null,就将起初的值先转为long，然后加一
            Long count = s != null ? Long.parseLong(s) + 1 : 1;
            hashOps.put("loginCount",format,count.toString());
            // 将用户信息存入到redis中
            try {
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                // 根据当前登录成功用户的id不同将用户信息存储到redis中
                if (operations.get(String.valueOf(u.getId())) == null) {
                    redisTemplate.opsForValue().set(String.valueOf(u.getId()), objectMapper.writeValueAsString(u), 60 * 60 * 24, TimeUnit.MINUTES);
                } else {
                    return new StatusUtil("当前已存在登录用户", null);
                }
                if (operations.get("role_" + u.getId()) == null) {
                    // 获取当前用户角色 根据当前用户id查询到角色id
                    Integer roleId = roleService.getRoleId(u.getId());
                    if (roleId != null) {
                        // 将roleId存入到redis中
                        redisTemplate.opsForValue().set("role_" + roleId, String.valueOf(roleId));
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if (rem) {
                Cookie cookie1 = new Cookie("username", username);
                cookie1.setMaxAge(24 * 60 * 60);
                response.addCookie(cookie1);
                Cookie cookie2 = new Cookie("password", password);
                cookie2.setMaxAge(24 * 60 * 60);
                response.addCookie(cookie2);
            } else {
                Cookie cookie1 = new Cookie("username", username);
                cookie1.setMaxAge(0);
                response.addCookie(cookie1);
                Cookie cookie2 = new Cookie("password", password);
                cookie2.setMaxAge(0);
                response.addCookie(cookie2);
            }
            u.setPassword(null);
            return new StatusUtil("登录成功", u);
        } else {
            return new StatusUtil("用户名或密码错误", null);
        }

    }


    @PostMapping("/updateUser")
    public StatusUtil updateUser(@RequestBody User u) {
        StatusUtil statusUtil = userService.upUser(u);
        if (statusUtil.getU() != null) {
            try {
                redisTemplate.opsForValue().set(String.valueOf(u.getId()), objectMapper.writeValueAsString(statusUtil.getU()), 60 * 60 * 24, TimeUnit.MINUTES);
                return statusUtil;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return new StatusUtil("网络出现异常，请稍后再试", null);
    }

    @GetMapping("/loginOut")
    public StatusUtil loginOut(@RequestParam("id") int id) {
        Boolean user = redisTemplate.delete(String.valueOf(id));
        Boolean roleId = redisTemplate.delete("role_" + id);
        if (Boolean.TRUE.equals(roleId) && Boolean.TRUE.equals(user)) {
            return new StatusUtil("注销成功", null);
        } else {
            return new StatusUtil("网络出现异常，请稍后再试", null);
        }
    }

    /*
    该方法是获取最近五天项目访问人数
     */
    @GetMapping("/getCount")
    public CountResult getAccessCount() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        List<String> numbers=new ArrayList<>();
        List<Long> score=new ArrayList<>();
        // 获取指定范围的字段和对应的值
        List<String> rangeValues = new ArrayList<>();
        Set<String> keys = hashOps.keys("loginCount");
        // 将set集合里面的元素放入list集合
        rangeValues.addAll(keys);
        for (int i=rangeValues.size()-5;i<rangeValues.size();i++){
            String s = rangeValues.get(i);
            numbers.add(s);
            String s1 = hashOps.get("loginCount", s);
            long l = Long.parseLong(s1);
            score.add(l);
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
