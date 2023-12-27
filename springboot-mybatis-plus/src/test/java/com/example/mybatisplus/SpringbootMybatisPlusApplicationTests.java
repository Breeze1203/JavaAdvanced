package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.mapper.UserDao;
import com.example.mybatisplus.model.User;
import com.example.mybatisplus.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class SpringbootMybatisPlusApplicationTests {


    @Autowired
    private UserDao userDao;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void contextLoads() {
        // 插入
        int result = userDao.insert(new User(null, "mysql", 23, new Date(), 3));
        System.out.println(result);
    }

    @Test
    void select() {
        /*
        查询参数为null
         */
//        List<User> user = userDao.selectList(null);
//        System.out.println(user);
//        // 根据主键id查询
//        User user1 = userDao.selectById(1);
//        System.out.println(user1);
        User userById = userDao.getUserById();
        System.out.println(userById);
    }

    @Test
    void selectBy() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        List<User> list = userService.list(wrapper);
        System.out.println(list);
        /*
        // 只查询表中为name的字段，其它属性为null
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("name");
        List<User> users = userDao.selectList(wrapper);
        System.out.println(users);

         */
        /*
        // 查询字段name为nacos
        QueryWrapper<User> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("name", "nacos");
        List<User> users1 = userDao.selectList(wrapper1);
        System.out.println(users1);

         */
        /*
       根据主键批量查询

        List<Integer> ids=new ArrayList<>();
        ids.add(1);
        ids.add(3);
        ids.add(4);
        List<User> users2 = userDao.selectBatchIds(ids);
        System.out.println(users2);

         */
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        //wrapper.eq(true,"name","nacos");
//        wrapper.ge("name","nacos");
//        List<User> users = userDao.selectList(wrapper);
//        System.out.println(users);

    }

    @Test
    void test01() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name", "naco")
                .between("age", 20, 25)
                .isNotNull("departmentId");
        // 组装条件查询 sql语句
        // SELECT id,date,departmentId,name,age FROM user
        // WHERE (name LIKE ? AND age BETWEEN ? AND ? AND departmentId IS NOT NULL)
        List<User> list = userService.list(wrapper);
        System.out.println(list);
    }

    @Test
    void test02() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("age");
        // SELECT id,date,departmentId,name,age FROM user ORDER BY age DESC
        List<User> list = userService.list(wrapper);
        System.out.println(list);
    }


    @Test
    void test03() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //将(年龄大于20并且用户名中包含有a)或邮箱为null的用户信息修改
        wrapper.ge("age", 20)
                .like("name", "ct")
                .or()
                .isNull("departmentId");
        int mq = userDao.update(new User(null, "nosql", 28, null, null), wrapper);
        System.out.println(mq);
    }

    @Test
    void test04() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //查询用户信息的username和age字段
        wrapper.select("name", "age");
        wrapper.ge("age", 25);
        List<Map<String, Object>> maps = userDao.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    public void test05() {
        //查询id小于等于3的用户信息
        //SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE (id IN
        //(select id from t_user where id <= 3))
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select id from user where id <= 3");
        List<User> list = userDao.selectList(queryWrapper);
        list.forEach(System.out::println);
    }
}
