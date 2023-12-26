package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.example.mybatisplus.mapper.UserDao;
import com.example.mybatisplus.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Date;
import java.util.List;

@SpringBootTest
class SpringbootMybatisPlusApplicationTests {

    @Autowired
    private UserDao userDao;

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
        List<User> user = userDao.selectList(null);
        System.out.println(user);
        // 根据主键id查询
        User user1 = userDao.selectById(1);
        System.out.println(user1);
    }

    @Test
    void selectBy() {
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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //wrapper.eq(true,"name","nacos");
        wrapper.ge("name","nacos");
        List<User> users = userDao.selectList(wrapper);
        System.out.println(users);

    }
}
