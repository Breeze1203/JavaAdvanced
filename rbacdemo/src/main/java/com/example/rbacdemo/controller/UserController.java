package com.example.rbacdemo.controller;


import com.example.rbacdemo.model.user;
import com.example.rbacdemo.permission.CheckPermission;
import com.example.rbacdemo.service.UserService;
import com.example.rbacdemo.util.StatusUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Resource(name = "UserService")
    private UserService userService;

    // 登录认证的方法
    @GetMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("remember") String remember, HttpServletResponse response) {
        user u = userService.getUserByName(username);
        boolean rem = Boolean.parseBoolean(remember);
        if (u != null && password.equals(u.getPassword())) {
            // 将用户信息存入到redis中
            try {
                ValueOperations<String, String> operations = redisTemplate.opsForValue();
                if(operations.get("user")==null){
                    redisTemplate.opsForValue().set("user",objectMapper.writeValueAsString(u));
                }
                if(operations.get("roleId")==null){
                    // 获取当前用户角色 根据当前用户id查询到角色id
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if (rem) {
                Cookie cookie1 = new Cookie("username", username);
                cookie1.setMaxAge(3 * 24 * 60 * 60);
                response.addCookie(cookie1);
                Cookie cookie2 = new Cookie("password", password);
                cookie2.setMaxAge(3 * 24 * 60 * 60);
                response.addCookie(cookie2);
            } else {
                Cookie cookie1 = new Cookie("username", username);
                cookie1.setMaxAge(0);
                response.addCookie(cookie1);
                Cookie cookie2 = new Cookie("password", password);
                cookie2.setMaxAge(0);
                response.addCookie(cookie1);
            }
            return StatusUtil.success_message;
        } else {
            return StatusUtil.failure_message;
        }

    }

    @GetMapping("/getUser")
    public user getUser() {
        // 因为每个登录用户的信息都存在user这个键中
        String s = redisTemplate.opsForValue().get("user");
        if (s == null) {
            return null;
        }else {
            try {
                // 直接返回当前登录用户的信息
                user user = objectMapper.readValue(s, user.class);
                return user;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("/hello")
    @CheckPermission(permission = "user")
    public String hello() {
        return "欢迎你";
    }

    @GetMapping("/per/emp")
    public String loin() {
        return "发送请求了";
    }
}
