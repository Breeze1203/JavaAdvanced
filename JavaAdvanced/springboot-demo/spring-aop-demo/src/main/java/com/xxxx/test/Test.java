package com.xxxx.test;

import com.xxxx.dao.UserDao;
import com.xxxx.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) {

        // 获取上下文环境
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        // 获取指定Bean
        UserService userService = (UserService) ac.getBean("userService");
        userService.test();

        /*UserDao userDao = (UserDao) ac.getBean("userDao");
        userDao.test();*/

    }
}
