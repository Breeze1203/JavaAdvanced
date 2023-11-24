package com.example.admin.controller;


import com.example.admin.rocketmq.MessageProducer;
import com.example.admin.model.User;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.RoleService;
import com.example.admin.service.UserService;
import com.example.admin.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Resource(name = "RoleService")
    RoleService roleService;

    // 用户用户名登录认证的方法
    @GetMapping("/api/login")
    public StatusUtil login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("remember") String remember, HttpServletRequest request, HttpServletResponse response) {
        User u = userService.getUserByName(username);
        boolean rem = Boolean.parseBoolean(remember);
        if (u != null && password.equals(u.getPassword())) {
            return loginPublic(u,response,rem);
        } else {
            return new StatusUtil("用户名或密码错误", 401, null);
        }

    }

    // 短信验证码登录
    @GetMapping("/api/loginByPhone")
    public StatusUtil loginByPhone(@RequestParam("phone") String phone, @RequestParam("code") String code, @RequestParam("remember") String remember, HttpServletResponse response) {
        String s = redisTemplate.opsForValue().get("code_" + phone);
        if(s==null){
            return new StatusUtil("验证码已过期，请稍后再试",500,null);
        }else if(!s.equals(code)){
            return new StatusUtil("验证码错误，请稍后再试",500,null);
        }
        User userByPhone = userService.getUserByPhone(Long.parseLong(phone));
        boolean rem = Boolean.parseBoolean(remember);
        return loginPublic(userByPhone, response, rem);
    }


    @GetMapping("/api/loginOut")
    public StatusUtil loginOut(@RequestParam("id") Integer id) {
        // 删除token及用户信息
        Boolean token = redisTemplate.delete(id + "token");
        Boolean u = redisTemplate.delete("user_" + id);
        if (Boolean.TRUE.equals(token) && Boolean.TRUE.equals(u)) {
            return new StatusUtil("注销成功", 200, null);
        } else {
            return new StatusUtil("网络出现异常，请稍后再试", 500, null);
        }
    }

    /*
    该方法是获取最近五天项目访问人数
     */
    @GetMapping("/api/getCount")
    public CountResult getAccessCount() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        redisTemplate.opsForHash().delete("loginCount", "2023-10-23");
        List<String> numbers = new ArrayList<>();
        List<Long> score = new ArrayList<>();
        // 获取指定范围的字段和对应的值
        List<String> rangeValues = new ArrayList<>();
        Set<String> keys = hashOps.keys("loginCount");
        // 将set集合里面的元素放入list集合
        rangeValues.addAll(keys);
        if (rangeValues.size() > 5) {
            for (int i = rangeValues.size() - 5; i < rangeValues.size(); i++) {
                String s = rangeValues.get(i);
                numbers.add(s);
                String s1 = hashOps.get("loginCount", s);
                if (s1 != null) {
                    long l = Long.parseLong(s1);
                    score.add(l);
                }
            }
        } else {
            for (int i = 0; i < rangeValues.size(); i++) {
                String s = rangeValues.get(i);
                numbers.add(s);
                String s1 = hashOps.get("loginCount", s);
                if (s1 != null) {
                    long l = Long.parseLong(s1);
                    score.add(l);
                }
            }
        }
        return new CountResult(numbers, score);
    }

    //获取所有用户
    @PostMapping("/api/getAllUser")
    public List<User> getAllUser(@RequestBody User user) {
        return userService.getAllUser(user);
    }

    // 根据id是否禁用用户
    @CheckPermission(permission = "update_user")
    @PostMapping("/api/updateUser")
    public StatusUtil disable(@RequestBody User user) {
        if (user.getState() != null) {
            user.setState(!user.getState());
        }
        Integer i = userService.updateUser(user);
        if (i > 0) {
            return new StatusUtil("修改成功", 200, null);
        } else {
            return new StatusUtil("网络出现异常,请稍后再试", 500, null);
        }
    }

    @CheckPermission(permission = "delete_user")
    @GetMapping("/api/deleteUser")
    public StatusUtil deleteUser(@RequestParam("id") Integer id) {
        // 删除用户前，先判断该用户是否登录
        String s = redisTemplate.opsForValue().get("user_" + id);
        if (s != null) {
            return new StatusUtil("该用户已经登录，请稍后再试", 500, null);
        } else {
            Integer result = userService.deleteById(id);
            Integer i = roleService.deleteRoleById(id);
            if (result > 0 && i != null) {
                return new StatusUtil("删除成功", 200, null);
            } else {
                return new StatusUtil("网络出现异常,请稍后再试", 500, null);
            }
        }
    }

    @PostMapping("/api/addUser")
    @CheckPermission(permission = "add_user")
    public StatusUtil addUser(@RequestBody User user) {
        user.setState(true);
        Integer i = userService.insertUser(user);
        if (i > 0) {
            return new StatusUtil("添加成功", 200, null);
        } else {
            return new StatusUtil("网络出现异常,请稍后再试", 500, null);
        }
    }

    @PostMapping("/api/updateUserRole")
    public StatusUtil updateUserRole(@RequestParam("rid") Integer rid, @RequestParam("id") Integer id) {
        int i = roleService.updateUserById(rid, id);
        if (i > 0) {
            return new StatusUtil("修改成功", 200, null);
        } else {
            return new StatusUtil("网络出现异常,请稍后再试", 500, null);
        }
    }

    @PostMapping("/api/updatePassword")
    public StatusUtil updatePassword(@RequestBody User user) {
        Integer i = userService.updateUser(user);
        if (i > 0) {
            MessageProducer.pushMessage(user);
            return new StatusUtil("修改成功", 200, null);
        } else {
            return new StatusUtil("网络出现异常,请稍后再试", 500, null);
        }
    }

    @GetMapping("/api/getUserById")
    public StatusUtil updatePassword(@RequestParam("id") Integer id) {
        User user = userService.getUserById(id);
        return new StatusUtil(null, 200, user);
    }

    /*
    获取短信验证码
     */
    @GetMapping("/api/getVerification")
    public StatusUtil getVerification(@RequestParam("phone") String phone) {
        Long userPhone=null;
        try {
            userPhone = Long.parseLong(phone);
        }catch (Exception e){
            return new StatusUtil("请输入正确的手机号格式", 500, null);
        }
        // 查看有没有该手机号注册的用户
        User userByPhone = userService.getUserByPhone(userPhone);
        if (userByPhone!=null) {
            double v = new Random().nextDouble();
            int code= (int) (v*10000);
            MessageUtil.sendVerificationCode(phone,code);
            redisTemplate.opsForValue().set("code_"+phone,String.valueOf(code));
            // 设置5分钟过期
            redisTemplate.expire("code_"+phone,5, TimeUnit.MINUTES);
            return new StatusUtil("验证码发送成功，请注意接收", 200, null);
        } else {
            return new StatusUtil("该手机号尚未注册，请稍后再试", 500, null);
        }
    }

    private StatusUtil loginPublic(User u,HttpServletResponse response,boolean rem){
        String format = DateUtil.format(new Date());
        // 每次用户登录成功，访问次数加一
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String s = hashOps.get("loginCount", format);
        // 如果s不为null,就将起初的值先转为long，然后加一
        long count = s != null ? Long.parseLong(s) + 1 : 1;
        hashOps.put("loginCount", format, Long.toString(count));
        // 将token信息存入到redis中
        String token = JwtToken.generateToken(u);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        // 根据当前登录成功用户不同将用户信息变成token存储到redis中 因为用户id唯一
        String token_name = u.getId() + "token";
        if (operations.get(token_name) == null) {
            operations.set(token_name, token, 60 * 60 * 24, TimeUnit.MINUTES);
        } else {
            return new StatusUtil("当前已存在登录用户，请稍后再试", 500, null);
        }
        try {
            // 将用户信息存入缓存
            operations.set("user_" + u.getId(), objectMapper.writeValueAsString(u));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // 将用户信息生成token返回给前端
        Cookie c3 = new Cookie(u.getUsername() + "token", token);
        c3.setMaxAge(24 * 60 * 60);
        c3.setPath("/");
        c3.setDomain("localhost");
        response.addCookie(c3);
        if (rem) {
            Cookie c1 = new Cookie("username", u.getUsername());
            c1.setMaxAge(24 * 60 * 60);
            c1.setPath("/");
            c1.setDomain("localhost");
            response.addCookie(c1);
            Cookie c2 = new Cookie("password", u.getPassword());
            c2.setMaxAge(24 * 60 * 60);
            c2.setPath("/");
            c2.setDomain("localhost");
            response.addCookie(c2);
        } else {
            Cookie cookie1 = new Cookie("username", u.getUsername());
            cookie1.setMaxAge(0);
            response.addCookie(cookie1);
            cookie1.setPath("/");
            cookie1.setDomain("localhost");
            response.addCookie(cookie1);
            Cookie cookie2 = new Cookie("password", u.getPassword());
            cookie2.setMaxAge(0);
            cookie2.setPath("/");
            cookie2.setDomain("localhost");
            response.addCookie(cookie2);
        }
        // 将token放入到redis中
        redisTemplate.opsForValue().set(u.getUsername() + "token", token);
        // 设置键的生命周期为24小时
        redisTemplate.expire(u.getUsername() + "token", 24, TimeUnit.HOURS);
        u.setPassword(null);
        return new StatusUtil("登录成功", 200, u);
    }
}
