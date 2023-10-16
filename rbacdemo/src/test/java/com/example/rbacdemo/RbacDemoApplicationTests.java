package com.example.rbacdemo;

import com.example.rbacdemo.mapper.MenuMapper;
import com.example.rbacdemo.model.menu;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

@SpringBootTest
class RbacDemoApplicationTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name = "MenuMapper")
    MenuMapper mapper;
    @Test
    void contextLoads() {
        List<menu> menuByRole = mapper.getMenuByRole(1);
        System.out.println(menuByRole.size());
        System.out.println(menuByRole);
    }
    @Test
    void testRedis(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String s = operations.get("user");
        System.out.println(s);
    }

}
