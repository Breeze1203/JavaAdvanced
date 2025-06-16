package com.example.springbootdynamic.service;

import com.example.springbootdynamic.entity.Student;

import java.util.List;

public interface StudentService {
    public List<Student> getAllStudent();
    public void addStudent(Student student);
}
