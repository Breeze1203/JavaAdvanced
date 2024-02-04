package com.example.springsecurity.service.impl;

import com.example.springsecurity.model.User;
import com.example.springsecurity.service.LoginService;
import com.example.springsecurity.util.JwtUtil;
import com.example.springsecurity.util.StatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {
    @Resource(name = "Authentication")
    private AuthenticationManager authenticationManager;

    @Override
    public StatusUtil Login(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if(authenticate.isAuthenticated()){
            User principal = (User) authenticate.getPrincipal();
            return new StatusUtil("登录成功",200, principal);
        }else {
            return new StatusUtil("登录失败",200,null);
        }
    }
}
