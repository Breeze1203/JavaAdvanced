package com.example.springbootjpa;

import com.example.springbootjpa.entity.OrderMode;
import com.example.springbootjpa.entity.OrderModel;
import com.example.springbootjpa.entity.OrderType;
import com.example.springbootjpa.entity.Person;
import com.example.springbootjpa.repository.OrderRepo;
import com.example.springbootjpa.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;


@SpringBootApplication
@EnableAsync
public class SpringBootJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PersonRepository repository, OrderRepo orderRepo) {
        return args -> {
            Person person = new Person();
            person.setName("John");
            repository.save(person);
            Person save = repository.findById(person.getId()).orElseThrow();
            System.out.println(save);
            Page<Person> all = repository.findAll(PageRequest.of(0, 2));
            List<Person> list = all.stream().toList();
            System.out.println(list);
            OrderModel orderModel = new OrderModel();
            orderModel.setOrderId("aaa");
            orderModel.setType(OrderType.拼单);
            orderModel.setMode(OrderMode.EXCLUSIVE);
            orderModel.setPrice(4L);
            orderRepo.save(orderModel);
            OrderModel byId = orderRepo.findById(5L);
            System.out.println(byId.getType());
            System.out.println(byId.getMode().getPriceModePer());
            System.out.println(byId);
            ObjectMapper objectMapper = new ObjectMapper();
// 将对象转换为JSON字符串
            String json = objectMapper.writeValueAsString(byId);
// 打印或返回JSON字符串
            System.out.println(json);
        };
    }

}
