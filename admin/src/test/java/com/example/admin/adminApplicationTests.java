package com.example.admin;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.example.admin.mapper.*;
import com.example.admin.model.*;
import com.example.admin.service.AuthorizationService;
import com.example.admin.service.MessageService;
import com.example.admin.service.RoleService;
import com.example.admin.util.JwtToken;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class adminApplicationTests {

//    @Autowired
//    private UserMapper userMapper;

//    @Autowired
//    OrganizationMapper organizationMapper;

//    @Autowired
//    MessageMapper messageMapper;

    //    @Autowired
//    private UserRoleMapper userRoleMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //    //    @Resource(name = "MenuMapper")
////    MenuMapper mapper;
//    @Resource(name = "UserMessageMapper")
//    UserMessageMapper userMessageMapper;
//
////    @Autowired
////    OperationDataMapper operationDataMapper;
//
////    @Autowired
////    MessageService messageService;
//
//
//    @Test
//    void contextLoads() {
////        List<Menu> menuByRole = mapper.getMenuByRole(1);
////        System.out.println(menuByRole.size());
////        System.out.println(menuByRole);
//        //User admin = userMapper.getUserByName("admin");
//        //System.out.println(admin);
//    }
//
    @Test
    void testRedis() {
        redisTemplate.delete("1token");
    }
//    }
//
//    @Test
//    void testRole() {
////        Integer roleIdById = userRoleMapper.getRoleIdById(1);
////        System.out.println(roleIdById);
//        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
//        zSetOps.remove("count", "2023-10-22");
//    }
//
//    @Test
//    void testUpdate() {
////        User u = new User();
////        u.setEmail("1321321");
////        u.setPhone(12324324L);
////        u.setAddress("中国深圳");
////        u.setUsername("xxx");
////        u.setId(1);
////        int update = userMapper.update(u);
////        System.out.println(update);
//        //User admin = userMapper.getUserByName("admin");
//        //System.out.println(admin.getUsername());
//        Random random = new Random();
//        double v = random.nextDouble();
//        System.out.println(v * 100000);
//    }
//
//    @Test
//    void testIncrease() {
//        // 获取当前日期
//        Date currentDate = new Date();
//        // 创建格式化对象，指定日期格式
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        // 将日期格式化为特定格式的字符串
//        String formattedDate = sdf.format(currentDate);
//        // 输出结果
//        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
//        zSet.incrementScore("count", "2023-10-22", 1);
//        //System.out.println(zSet.score("count", formattedDate));
//    }
//
//    @Test
//    void getScore() {
//        Set<String> members = new HashSet<>();
//        List<Double> score = new ArrayList<>();
//        ZSetOperations<String, String> operations = redisTemplate.opsForZSet();
//        Set<String> count = operations.range("count", 0, -1);
//        for (String s : count) {
//            members.add(s);
//            double a = redisTemplate.opsForZSet().score("count", s);
//            System.out.println(a);
//            score.add(redisTemplate.opsForZSet().score("count", s));
//        }
//        System.out.println(members.size());
//        System.out.println(members.size());
//    }
//
//    // 用hash对登录人进行统计
//    @Test
//    void getLoginCount() {
//        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
//        String hashKey = "loginCount"; // 哈希表的键
//// 存入 "2023-10-21" 的数据并自增
//        String dateKey1 = "2023-10-22";
//        String value1 = hashOps.get(hashKey, dateKey1);
//        Long newValue1 = value1 != null ? Long.parseLong(value1) + 3 : 1;
//        hashOps.put(hashKey, dateKey1, newValue1.toString());
//// 存入 "2023-10-22" 的数据并自增
//        String dateKey2 = "2023-10-23";
//        String value2 = hashOps.get(hashKey, dateKey2);
//        Long newValue2 = value2 != null ? Long.parseLong(value2) + 3 : 1;
//        hashOps.put(hashKey, dateKey2, newValue2.toString());
//// 获取哈希表中的所有键和值
//        Map<String, String> hashEntries = hashOps.entries(hashKey);
//        for (Map.Entry<String, String> entry : hashEntries.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            System.out.println("Key: " + key + ", Value: " + value);
//        }
//
//    }
//
//    @Test
//    void getIndex() {
//        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
//        String hashKey = "loginCount"; // 哈希表的键
//// 定义范围
//        int startIndex = 1; // 范围开始索引（从0开始）
//        int endIndex = 3; // 范围结束索引（包括结束索引）
//// 获取指定范围的字段和对应的值
//        List<String> rangeValues = new ArrayList<>();
//        Set<String> keys = hashOps.keys(hashKey);
//        for (String s : keys) {
//            rangeValues.add(s);
//        }
//        for (int i = rangeValues.size() - 5; i < rangeValues.size(); i++) {
//            String s = rangeValues.get(i);
//            System.out.println(rangeValues.get(i));
//            System.out.println(hashOps.get("loginCount", s));
//        }
//    }
//
//    @Autowired
//    RoleMapper roleMapper;
//
//    @Test
//    void getRole() {
//        // 测试根据id获取身份信息
//        String roleById = roleMapper.getRoleById(1);
//        System.out.println(roleById);
//    }
//
//
//    // 测试登录日志插入
//    @Test
//    void insertLog() {
//        OperationData operationData = new OperationData();
//        operationData.setId(1);
//        operationData.setType("xxcd");
////        operationData.setLogin_user("admin");
////        int i = logDataMapper.insertLogin(operationData);
////        System.out.println(i);
//    }
//
//    // 测试查询日志
//    @Test
//    void selectLogData() {
//        //List<OperationData> logData = logDataMapper.getLogData();
//        //System.out.println(logData);
//    }
//
//    // 测试生成token
//    @Test
//    void generateToken() {
//        User user = new User();
//        user.setUsername("admin");
//        user.setEmbod("xxx");
//        user.setPhone(19243243L);
//        user.setAddress("中国深圳");
//        user.setUserFace("csdfsdf");
//        user.setId(5);
//        String s = JwtToken.generateToken(user);
//        System.out.println(s);
//        Integer b = JwtToken.verifyToken(s);
//        System.out.println(b);
//    }
//
//    // 删除日志
//    @Test
//    void deleteLog() {
////        Integer i = operationDataMapper.deleteLog();
////        System.out.println(i);
//    }
//
//    // 部门查询所有
//    @Test
//    void getOrganization() {
//        //List<Organization> all = organizationMapper.getAll(-1);
//        //System.out.println(all);
//    }
//
//    // 获取所有用户
//    @Test
//    void getAllUser() {
////        List<User> allUser = userMapper.getAllUser();
////        System.out.println(allUser);
////        List<User> userByOid = userMapper.getUserByoId(6);
////        System.out.println(userByOid);
//    }
//
//    // 是否禁用用户
//    @Test
//    void disableUser() {
//        //userMapper.disableUser(3,true);
////        Integer i = userMapper.deleteById(3);
////        System.out.println(i);
//    }
//
//    // 查询用户
//    @Test
//    void getUser() {
//        User user = new User();
////        user.setUsername("breeze");
////        user.setState(true);
//       // List<User> allUser = userMapper.getAllUser(new User());
//        //System.out.println(allUser);
//    }
//
//    @Resource(name = "AuthorizationMapper")
//    AuthorizationMapper authorizationMapper;
//
//    @Resource(name = "AuthorizationService")
//    AuthorizationService authorizationService;
//
//    // 查看所有权限
//    @Test
//    void getAllPer() {
////        List<Authorization> allPermission = authorizationMapper.getAllPermission();
////        System.out.println(allPermission);
//        List<Authorization> permissionByrId = authorizationService.getPermissionByrId(1);
//        System.out.println(permissionByrId);
//    }
//
//    @Autowired
//    RoleService roleService;
//
//    // 测试根据用户id获取角色id
//    @Test
//    void getRoleId() {
//        Integer ridByuId = roleService.getRidByuId(1);
//        List<Authorization> permissionByrId = authorizationMapper.getPermissionByrId(ridByuId);
//        System.out.println(permissionByrId);
//    }
//
//    @Autowired
//    RoleAuthorizationMapper roleAuthorizationMapper;
//
//
//    // 测试根据角色，插入权限
//    @Test
//    void insertPermission() {
//        Integer role = userRoleMapper.getRole(22);
//        System.out.println(role);
//    }
//
//    // 测试发短信
//    @Test
//    void sendMessage() throws Exception {
//        Config config = new Config();
//        // 必填，您的 AccessKey ID
//        config.setAccessKeyId("LTAI5tCKYkSjSgVnawvexE64");
//        config.setAccessKeySecret("6XOoX8RiVKtSYvEjem8YVq4B32Bf68");
//        config.endpoint = "dysmsapi.aliyuncs.com";
//        Client client = new Client(config);
//        SendSmsRequest sendSmsRequest = new SendSmsRequest();
//        sendSmsRequest.setPhoneNumbers("19972552055");
//        sendSmsRequest.setSignName("adminflow");
//        sendSmsRequest.setTemplateCode("SMS_463720732");
//        sendSmsRequest.setTemplateParam("{\"user\":\"1234\",\"password\":\"123\"}");
//        client.sendSms(sendSmsRequest);
//    }
//
//    // 获取私信消息
//    @Test
//    void getMessage() {
//        HashMap<String, Object> map = new HashMap<>();
//        String s = "1,2,3,4,5";
//        String[] split = s.split(",");
//        map.put("id", 1);
//        map.put("messageIds", null);
//        //List<Message> message = messageMapper.getMessage(map);
//        //System.out.println(message);
//    }
//
//    // 测试私信消息查询
//    @Test
//    void getUserMessage() {
////        UserMessage userMessage = userMessageMapper.getUserMessage(2);
////        System.out.println(userMessage);
////        UserMessage userMessage = new UserMessage();
////        userMessage.setUId(2);
////        userMessage.setMId("1,2,3");
////        Integer i = userMessageMapper.saveUserMessage(userMessage);
////        System.out.println(i);
////        List<Message> allMessage = messageService.getAllMessage(1);
////        System.out.println(allMessage);
//    }
//
//    @Resource(name = "MenuMapper")
//    private MenuMapper mapper;
//
//    // 测试菜单查询
//    @Test
//    void getMenus() {
//        //System.out.println(mapper.getAllMenus().size());
//    }
//
//    // 测试树状结构菜单查询
//    @Test
//    void getTree(){
//       //
//    }
//
//    @Test
//    void getPermissionCount(){
////        Long permissionCount = authorizationMapper.getPermissionCount();
////        System.out.println(permissionCount);
////        List<Authorization> allPermission = authorizationMapper.getAllPermissionByPage(6, 0);
////        System.out.println(allPermission);


    @Resource(name = "MenuMapper")
    private MenuMapper mapper;

    @Resource(name = "UserMapper")
    private UserMapper userMapper;
    @Test
    void getMenu(){
//        Menu menu = new Menu();
//        menu.setMenu_name("用户");
//        List<Menu> menuByCondition = mapper.getMenuByCondition(menu);
//        System.out.println(menuByCondition);
        User userById = userMapper.getUserById(1);
        System.out.println(userById);
    }

    @Test
    void getTree(){
        List<Organization> list = Arrays.asList(
                new Organization(1, "总部", -1),
                new Organization(2, "财务部", 1),
                new Organization(3, "人力资源部", 1),
                new Organization(4, "市场部", 1),
                new Organization(5, "技术部", 1),
                new Organization(6, "研发部", 5),
                new Organization(7, "测试部", 6)
        );

        Organization root = Organization.covertToTree(list);
        System.out.println(root);
    }



}
