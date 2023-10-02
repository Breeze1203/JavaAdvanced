package org.example.user;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service("UserService")
public class UserService {
    // 这里应该是通过restTemplate远程调用获取到的请求结果 这里是假数据
    @HystrixCommand
    public List<User> getAll(List<Integer> id){
        List<User> users = new ArrayList<>();
        for (Integer i:id) {
            User user = new User(i, "李白", i);
            users.add(user);
        }
        return users;
    }

    // 基于注解进行批处理 batchMethod指定批处理的方法
    @HystrixCollapser(batchMethod = "getAll", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "1000"),
            @HystrixProperty(name = "maxRequestsInBatch", value = "10")
    })
    public Future<User> getUserById(Integer id) {
        // This method will be collapsed and the batchMethod "getProductsByIds" will be invoked
        return null;
    }
}
