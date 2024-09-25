package com.example.springbootjpa.repository;

import com.example.springbootjpa.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PersonRepository extends Repository<Person, Long>, CrudRepository<Person, Long> , PagingAndSortingRepository<Person, Long> {

    Person save(Person person);
    Optional<Person> findById(long id);

}

