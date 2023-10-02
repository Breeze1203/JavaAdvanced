package org.example.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.example.config.MergerConfig;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource(name = "UserService")
    UserService userService;

    @RequestMapping("/user")
    public List<User> getUser() throws InterruptedException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        List<User> u=new ArrayList<>();
        MergerConfig user1 = new MergerConfig(1, userService);
        MergerConfig user2 = new MergerConfig(2, userService);
        MergerConfig user3 = new MergerConfig(3, userService);
        Future<User> queue1 = user1.queue();
        Future<User> queue2 = user2.queue();
        Future<User> queue3 = user3.queue();
        try {
            User u1 = queue1.get();
            User u2 = queue2.get();
            User u3 = queue3.get();
            u.add(u1);
            u.add(u2);
            u.add(u3);
            hystrixRequestContext.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return u;
    }
}
