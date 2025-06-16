package com.example.springbootdynamic.controller;

import com.example.springbootdynamic.annotation.DataSelect;
import com.example.springbootdynamic.config.DataSourceContextHolder;
import com.example.springbootdynamic.entity.Student;
import com.example.springbootdynamic.service.impl.StudentServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DynamicSwitchController {
    @Resource
    private StudentServiceImpl studentService;

    @GetMapping("/switchDataSource/{datasourceName}")
    public String switchDataSource(@PathVariable("datasourceName") String datasourceName) {
        DataSourceContextHolder.setDataSource(datasourceName);
        List<Student> allStudent = studentService.getAllStudent();
        DataSourceContextHolder.removeDataSource();
        return allStudent.toString();
    }

    @DataSelect
    @GetMapping("/getStudentInSecurity")
    public String getStudentBySecurity() {
        List<Student> allStudent = studentService.getAllStudent();
        return allStudent.toString();
    }

    @DataSelect(value = "slave")
    @GetMapping("/getStudentInSpringSecurity")
    public String getStudent() {
        List<Student> allStudent = studentService.getAllStudent();
        return allStudent.toString();
    }
}
