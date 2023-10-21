package com.example.adminflow;

import com.example.adminflow.mapper.MenuMapper;
import com.example.adminflow.mapper.UserMapper;
import com.example.adminflow.mapper.UserRoleMapper;
import com.example.adminflow.model.User;
import com.example.adminflow.model.Menu;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class adminFlowDemoApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name = "MenuMapper")
    MenuMapper mapper;
    @Test
    void contextLoads() {
        List<Menu> menuByRole = mapper.getMenuByRole(1);
        System.out.println(menuByRole.size());
        System.out.println(menuByRole);
    }
    @Test
    void testRedis(){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        Set<byte[]> keys = connection.keys("*".getBytes());
        for (byte[] key : keys) {
            connection.del(key);
        }
        connection.close();
    }
    @Test
    void testRole(){
        Integer roleIdById = userRoleMapper.getRoleIdById(1);
        System.out.println(roleIdById);
    }

    @Test
    void testUpdate(){
        User u = new User();
        u.setEmail("1321321");
        u.setPhone(12324324L);
        u.setAddress("中国深圳");
        u.setUsername("xxx");
        u.setId(1);
        int update = userMapper.update(u);
        System.out.println(update);
    }

    @Test
    void testIncrease(){
        // 获取当前日期
        Date currentDate = new Date();
        // 创建格式化对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 将日期格式化为特定格式的字符串
        String formattedDate = sdf.format(currentDate);
        // 输出结果
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        zSet.incrementScore("count","2023-10-26",1);
        //System.out.println(zSet.score("count", formattedDate));
    }
    @Test
    void getScore(){
        Set<String> members = new HashSet<>();
        Set<Double> scores = new HashSet<>();
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        Set<String> count = operations.range("count", 0, -1);
        for (String s:count) {
            members.add(s);
            scores.add(redisTemplate.opsForZSet().score("count", s));
        }
        System.out.println(members.size());
        System.out.println(members.size());
    }
}
