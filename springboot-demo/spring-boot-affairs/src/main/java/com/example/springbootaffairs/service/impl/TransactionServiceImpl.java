package com.example.springbootaffairs.service.impl;

import com.example.springbootaffairs.entity.Student;
import com.example.springbootaffairs.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl {
    @Resource(name = "StudentService")
    StudentServiceImpl studentService;

    @Resource(name = "UserService")
    UserServiceImpl userService;

    @Transactional
    public void addUser() {
        System.out.println("add user");
        userService.addUser(new User("pt", "3548297839@qq.com"));
        try {
            studentService.addStudent(new Student("ce", "343", "3423", 22));
        }catch (Exception e){
            System.out.println("---");
        }
    }
    // TransactionServiceB
//    @Transactional
//    public void addStudent(){
//        System.out.println("add student");
//        studentService.addStudent(new Student("张三","3435@qq.com","423432",19));
//        throw new RuntimeException();
//    }
}
