package com.example.admin;

import com.example.admin.mapper.*;
import com.example.admin.model.OperationData;
import com.example.admin.model.User;
import com.example.admin.util.JwtToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;


import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class adminApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
//    @Resource(name = "MenuMapper")
//    MenuMapper mapper;

    @Autowired
    OperationDataMapper operationDataMapper;

    @Test
    void contextLoads() {
//        List<Menu> menuByRole = mapper.getMenuByRole(1);
//        System.out.println(menuByRole.size());
//        System.out.println(menuByRole);
        User admin = userMapper.getUserByName("admin");
        System.out.println(admin);
    }

    @Test
    void testRedis() {
        redisTemplate.delete("1token");
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        Set<byte[]> keys = connection.keys("*".getBytes());
//        for (byte[] key : keys) {
//            connection.del(key);
//        }
//        connection.close();
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
//        User u = new User();
//        u.setEmail("1321321");
//        u.setPhone(12324324L);
//        u.setAddress("中国深圳");
//        u.setUsername("xxx");
//        u.setId(1);
//        int update = userMapper.update(u);
//        System.out.println(update);
        //User admin = userMapper.getUserByName("admin");
        //System.out.println(admin.getUsername());
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
        String dateKey1 = "2023-10-22";
        String value1 = hashOps.get(hashKey, dateKey1);
        Long newValue1 = value1 != null ? Long.parseLong(value1) + 3 : 1;
        hashOps.put(hashKey, dateKey1, newValue1.toString());
// 存入 "2023-10-22" 的数据并自增
        String dateKey2 = "2023-10-23";
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

    @Test
    void getIndex(){
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String hashKey = "loginCount"; // 哈希表的键
// 定义范围
        int startIndex = 1; // 范围开始索引（从0开始）
        int endIndex = 3; // 范围结束索引（包括结束索引）
// 获取指定范围的字段和对应的值
        List<String> rangeValues = new ArrayList<>();
        Set<String> keys = hashOps.keys(hashKey);
        for (String s:keys) {
            rangeValues.add(s);
        }
        for (int i=rangeValues.size()-5;i<rangeValues.size();i++){
            String s = rangeValues.get(i);
            System.out.println(rangeValues.get(i));
            System.out.println(hashOps.get("loginCount",s));
        }
    }

    @Autowired
    RoleMapper roleMapper;

    @Test
    void getRole(){
        // 测试根据id获取身份信息
        String roleById = roleMapper.getRoleById(1);
        System.out.println(roleById);
    }



    // 测试登录日志插入
    @Test
    void insertLog(){
        OperationData operationData = new OperationData();
        operationData.setId(1);
        operationData.setType("xxcd");
//        operationData.setLogin_user("admin");
//        int i = logDataMapper.insertLogin(operationData);
//        System.out.println(i);
    }

    // 测试查询日志
    @Test
    void selectLogData(){
        //List<OperationData> logData = logDataMapper.getLogData();
        //System.out.println(logData);
    }

    // 测试生成token
    @Test
    void generateToken(){
        User user = new User();
        user.setUsername("admin");
        user.setEmbod("xxx");
        user.setPhone(19243243L);
        user.setAddress("中国深圳");
        user.setUserFace("csdfsdf");
        user.setId(5);
        String s = JwtToken.generateToken(user);
        System.out.println(s);
        Integer b= JwtToken.verifyToken(s);
        System.out.println(b);
    }
    // 删除日志
    @Test
    void deleteLog(){
        Integer i = operationDataMapper.deleteLog();
        System.out.println(i);
    }

}