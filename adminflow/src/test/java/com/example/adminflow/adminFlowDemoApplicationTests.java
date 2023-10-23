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
import org.springframework.data.redis.core.*;

import java.text.SimpleDateFormat;
import java.util.*;

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
    void testRedis() {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        Set<byte[]> keys = connection.keys("*".getBytes());
        for (byte[] key : keys) {
            connection.del(key);
        }
        connection.close();
    }

    @Test
    void testRole() {
//        Integer roleIdById = userRoleMapper.getRoleIdById(1);
//        System.out.println(roleIdById);
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        zSetOps.remove("count", "2023-10-22");
    }

    @Test
    void testUpdate() {
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
    void testIncrease() {
        // 获取当前日期
        Date currentDate = new Date();
        // 创建格式化对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 将日期格式化为特定格式的字符串
        String formattedDate = sdf.format(currentDate);
        // 输出结果
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        zSet.incrementScore("count", "2023-10-22", 1);
        //System.out.println(zSet.score("count", formattedDate));
    }

    @Test
    void getScore() {
        Set<String> members = new HashSet<>();
        List<Double> score = new ArrayList<>();
        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
        Set<String> count = operations.range("count", 0, -1);
        for (String s : count) {
            members.add(s);
            System.out.println(s);
            double a = redisTemplate.opsForZSet().score("count", s);
            System.out.println(a);
            score.add(redisTemplate.opsForZSet().score("count", s));
        }
        System.out.println(members.size());
        System.out.println(members.size());
    }

    // 用hash对登录人进行统计
    @Test
    void getLoginCount() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String hashKey = "loginCount"; // 哈希表的键
// 存入 "2023-10-21" 的数据并自增
        String dateKey1 = "2023-10-21";
        String value1 = hashOps.get(hashKey, dateKey1);
        Long newValue1 = value1 != null ? Long.parseLong(value1) + 1 : 1;
        hashOps.put(hashKey, dateKey1, newValue1.toString());
// 存入 "2023-10-22" 的数据并自增
        String dateKey2 = "2023-10-22";
        String value2 = hashOps.get(hashKey, dateKey2);
        Long newValue2 = value2 != null ? Long.parseLong(value2) + 3 : 1;
        hashOps.put(hashKey, dateKey2, newValue2.toString());
// 获取哈希表中的所有键和值
        Map<String, String> hashEntries = hashOps.entries(hashKey);
        for (Map.Entry<String, String> entry : hashEntries.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }

    }
}
