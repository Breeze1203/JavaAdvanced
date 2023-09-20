package org.example.sort;

import java.util.Objects;

public class Student implements Comparable<Student> {
    public String name;
    public int score;

    @Override
    public int compareTo(Student o) {
        int i = o.score - this.score;
        return i;
    }

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(name, student.name) && Objects.equals(score, student.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
