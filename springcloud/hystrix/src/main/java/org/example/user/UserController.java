package org.example.user;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
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

    @RequestMapping("/")
    public List<User> getUser() throws InterruptedException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        List<User> u=new ArrayList<>();
        // 这里相当于三个请求
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
            Thread.sleep(2000);
            MergerConfig user4 = new MergerConfig(4, userService);
            Future<User> queue4 = user4.queue();
            User user = queue4.get();
            System.out.println(user);
            u.add(u1);
            u.add(u2);
            u.add(u3);
            u.add(user);
            hystrixRequestContext.close();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return u;
    }

    @RequestMapping("/two")
    public List<User> getUsers() throws InterruptedException, ExecutionException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        List<User> u=new ArrayList<>();
        Future<User> userById = userService.getUserById(1);
        Future<User> userBy1 = userService.getUserById(2);
        Future<User> userBy2 = userService.getUserById(3);
        Future<User> userBy3 = userService.getUserById(4);
        User user = userById.get();
        User user1 = userBy1.get();
        User user2 = userBy2.get();
        User user3 = userBy3.get();
        System.out.println(user);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println(user1);
        Thread.sleep(2000);
        Future<User> userBy4 = userService.getUserById(5);
        User user4 = userBy4.get();
        System.out.println(user4);
        u.add(user);
        u.add(user1);
        u.add(user2);
        u.add(user3);
        u.add(user4);
        hystrixRequestContext.close();
        return u;
    }
}
