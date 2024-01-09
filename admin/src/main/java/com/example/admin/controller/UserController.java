package com.example.admin.controller;


import com.example.admin.model.User;
import com.example.admin.permission.CheckPermission;
import com.example.admin.service.RoleService;
import com.example.admin.service.UserService;
import com.example.admin.util.*;
import io.swagger.annotations.Api;
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
import java.util.regex.Pattern;


@Api(value = "用户Controller")
@RestController
public class UserController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "UserService")
    private UserService userService;

    @Resource(name = "RoleService")
    private RoleService roleService;

    /*
    用户名登录认证
     */
    @GetMapping("/login")
    public StatusUtil login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("remember") String remember, HttpServletRequest request, HttpServletResponse response) {
        User u = userService.getUserByName(username);
        boolean rem = Boolean.parseBoolean(remember);
        if (u != null && password.equals(u.getPassword())) {
            return loginPublic(u, response, rem);
        } else {
            return new StatusUtil(StatusMessage.AUTHENTICATION_FAILED.getMessage(), 401, null);
        }

    }

    /*
    短信验证码登录
     */
    @GetMapping("/loginByPhone")
    public StatusUtil loginByPhone(@RequestParam("phone") String phone, @RequestParam("code") String code, @RequestParam("remember") String remember, HttpServletResponse response) {
        String s = redisTemplate.opsForValue().get("code_" + phone);
        if (s == null) {
            return new StatusUtil(StatusMessage.CODE_EXPIRED.getMessage(), 500, null);
        }
        if (code.equals(s)) {
            User userByPhone = userService.getUserByPhone(Long.parseLong(phone));
            boolean rem = Boolean.parseBoolean(remember);
            return loginPublic(userByPhone, response, rem);
        } else {
            return new StatusUtil(StatusMessage.CODE_ERROR.getMessage(), 500, null);
        }
    }

    /*
    注销登录
     */
    @GetMapping("/loginOut")
    public StatusUtil loginOut(@RequestParam("id") Integer id) {
        // 删除token及用户信息
        Boolean token = redisTemplate.delete(id + "token");
        if (Boolean.TRUE.equals(token)) {
            return new StatusUtil(StatusMessage.LOGIN_OUT_SUCCESS.getMessage(), 200, null);
        } else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
        }
    }

    /*
    该方法是获取最近五天项目访问人数
     */
    @GetMapping("/getCount")
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

    /*
    获取所有用户
     */
    @PostMapping("/getAllUser")
    public List<User> getAllUser(@RequestBody User user) {
        return userService.getAllUser(user);
    }

    /*
    根据id是否禁用用户
     */
    @CheckPermission(permission = "update_user")
    @PostMapping("/updateUser")
    public StatusUtil disable(@RequestBody User user) {
        if (user.getState() != null) {
            user.setState(!user.getState());
        }
        Integer i = userService.updateUser(user);
        if (i > 0) {
            return new StatusUtil(StatusMessage.UPDATE_SUCCESS.getMessage(), 200, null);
        } else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
        }
    }

    /*
    删除用户
     */
    @CheckPermission(permission = "delete_user")
    @GetMapping("/deleteUser")
    public StatusUtil deleteUser(@RequestParam("id") Integer id) {
        // 删除用户前，先判断该用户是否登录
        String s = redisTemplate.opsForValue().get("user_" + id);
        if (s != null) {
            return new StatusUtil(StatusMessage.LOGIN_EXISTS.getMessage(), 500, null);
        } else {
            Integer result = userService.deleteById(id);
            Integer i = roleService.deleteRoleById(id);
            if (result > 0 && i != null) {
                return new StatusUtil(StatusMessage.DELETE_SUCCESS.getMessage(), 200, null);
            } else {
                return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
            }
        }
    }

    /*
    添加用户
     */
    @PostMapping("/addUser")
    @CheckPermission(permission = "add_user")
    public StatusUtil addUser(@RequestBody User user) {
        user.setState(true);
        Integer i = userService.insertUser(user);
        if (i > 0) {
            return new StatusUtil(StatusMessage.ADD_SUCCESS.getMessage(), 200, null);
        } else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
        }
    }

    @PostMapping("/updateUserRole")
    public StatusUtil updateUserRole(@RequestParam("rid") Integer rid, @RequestParam("id") Integer id) {
        int i = roleService.updateUserById(rid, id);
        if (i > 0) {
            return new StatusUtil(StatusMessage.UPDATE_SUCCESS.getMessage(), 200, null);
        } else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
        }
    }

    /*
    更新用户密码
     */
    @PostMapping("/updatePassword")
    public StatusUtil updatePassword(@RequestBody User user) {
        Integer i = userService.updateUser(user);
        if (i > 0) {
            // MessageProducer.pushMessage(user);
            return new StatusUtil(StatusMessage.UPDATE_SUCCESS.getMessage(), 200, null);
        } else {
            return new StatusUtil(StatusMessage.NETWORK_ERROR.getMessage(), 500, null);
        }
    }

    /*
    根据id获取用户信息
     */
    @GetMapping("/getUserById")
    public StatusUtil updatePassword(@RequestParam("id") Integer id) {
        User user = userService.getUserById(id);
        return new StatusUtil(null, 200, user);
    }

    /*
    获取短信验证码
     */
    @GetMapping("/getVerification")
    public StatusUtil getVerification(@RequestParam("phone") String phone) {
        String pattern = "^1\\d{10}$"; // 匹配以1开头，后面跟着10位数字的手机号码
        boolean matches = Pattern.matches(pattern, phone);
        if (!matches) {
            return new StatusUtil(StatusMessage.WRONG_FORMAT.getMessage(), 500, null);
        }
        // 查看有没有该手机号注册的用户
        User userByPhone = userService.getUserByPhone(Long.parseLong(phone));
        if (userByPhone != null) {
            double v = new Random().nextDouble();
            int code = (int) (v * 10000);
            MessageUtil.sendVerificationCode(phone, code);
            redisTemplate.opsForValue().set("code_" + phone, String.valueOf(code));
            // 设置5分钟过期
            redisTemplate.expire("code_" + phone, 5, TimeUnit.MINUTES);
            return new StatusUtil(StatusMessage.CODE_SUCCESS.getMessage(), 200, null);
        } else {
            return new StatusUtil(StatusMessage.PONE_NOT_REGISTER.getMessage(), 500, null);
        }
    }

    private StatusUtil loginPublic(User u, HttpServletResponse response, boolean rem) {
        String format = DateUtil.format(new Date());
        // 每次用户登录成功，访问次数加一
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String s = hashOps.get("loginCount", format);
        // 如果s不为null,就将起初的值先转为long，然后加一
        long count = s != null ? Long.parseLong(s) + 1 : 1;
        hashOps.put("loginCount", format, Long.toString(count));
        // 将用户信息生成token
        String token = JwtToken.generateToken(u);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        // 根据当前登录成功用户不同将用户信息变成token存储到redis中 因为用户id唯一
        String token_name = u.getId() + "token";
        if (operations.get(token_name) == null) {
            redisTemplate.opsForValue().set(token_name, token);
            redisTemplate.expire(token_name, 60 * 24, TimeUnit.MINUTES);
        } else {
            return new StatusUtil(StatusMessage.LOGIN_EXISTS.getMessage(), 500, null);
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
        u.setPassword(null);
        return new StatusUtil(StatusMessage.LOGIN_SUCCESS.getMessage(), 200, u);
    }

    /*
    获取当前登录用户除外的所有用户
     */
    @GetMapping("/WithOutUser")
    public List<User> getOutLogin(@RequestParam("id") Integer id) {
        return userService.getOutLogin(id);
    }


}
