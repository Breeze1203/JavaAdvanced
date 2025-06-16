package com.example.springbootdynamic.dao;

import com.example.springbootdynamic.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "StudentMapper")
public interface StudentMapper {
    List<Student> getAllStudent();

    void addStudent(Student student);
}
