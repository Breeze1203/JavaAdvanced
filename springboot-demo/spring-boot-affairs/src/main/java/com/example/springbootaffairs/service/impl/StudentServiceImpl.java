package com.example.springbootaffairs.service.impl;

import com.example.springbootaffairs.dao.StudentMapper;
import com.example.springbootaffairs.entity.Student;
import com.example.springbootaffairs.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "StudentService")
public class StudentServiceImpl implements StudentService {
    @Resource(name = "StudentMapper")
    private StudentMapper studentMapper;

    @Override
    public List<Student> getAllStudent() {
        return studentMapper.getAllStudent();
    }


    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void addStudent(Student student) {
        studentMapper.addStudent(student);
        throw new RuntimeException();
    }
}
