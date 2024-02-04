package com.example.springsecurity.config;

import com.example.springsecurity.mapper.UserMapper;
import com.example.springsecurity.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service(value = "UserDetailService")
public class MyUserDetailService implements UserDetailsService {

    @Resource(name = "UserMapper")
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.getUserByUserName(s);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<String> authorities=new ArrayList<>();
        authorities.add("admin");
        authorities.add("guest");
        user.setAuthorities(authorities);
        return user;
    }
}
