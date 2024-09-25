package com.example.springbootjpa;

import com.example.springbootjpa.entity.Person;
import com.example.springbootjpa.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


@SpringBootApplication
public class SpringBootJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PersonRepository repository) {
        return args -> {
            Person person = new Person();
            person.setName("John");
            repository.save(person);
            Person save = repository.findById(person.getId()).orElseThrow();
            System.out.println(save);
            Page<Person> all = repository.findAll(PageRequest.of(0, 2));
            List<Person> list = all.stream().toList();
            System.out.println(list);
        };
    }

}
