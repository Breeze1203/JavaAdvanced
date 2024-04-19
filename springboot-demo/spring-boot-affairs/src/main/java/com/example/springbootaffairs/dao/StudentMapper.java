package com.example.springbootaffairs.dao;

import com.example.springbootaffairs.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "StudentMapper")
public interface StudentMapper {
    List<Student> getAllStudent();

    void addStudent(Student student);
}
