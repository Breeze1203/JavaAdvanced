package org.example.user;

import com.netflix.hystrix.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * List<Product>：这是合并请求的响应类型。表示将多个单独的请求合并后得到的响应类型。
 * 在这个例子中，合并请求的响应是一个包含多个Product对象的列表。
 * Product：这是单个请求的响应类型。表示单个请求的响应类型。在这个例子中，
 * 每个单独的请求的响应是一个Product对象。
 * Integer：这是合并请求的参数类型。表示合并请求所使用的参数类型。
 * 在这个例子中，合并请求的参数是一个Integer类型的值，用于标识要获取的产品的ID。
 */


public class MergerConfig extends HystrixCollapser<List<User>, User, Integer> {
    // id为请求的参数  usersrvice是HystrixCommand示例中要用到的
    private Integer id;
    private UserService userService;

    public MergerConfig(Integer id, UserService userService) {
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("MergerConfig")).andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200)));
        this.id = id;
        this.userService = userService;
    }

    public MergerConfig(int number, UserService userService) {
        this.userService=userService;
        this.id=number;
    }

    // 用于获取合并请求的参数
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    // 其中包含了合并请求的信息,包括请求参数
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collection) {
        // 将请求参数放在一个集合里面
        System.out.println("  请求参数个数为："+collection.size());
        List<Integer> ids = new ArrayList<>(collection.size());
        for (CollapsedRequest<User,Integer> userIntegerCollapsedRequest: collection) {
            ids.add(userIntegerCollapsedRequest.getArgument());
        }
        System.out.println("-------");
        // 返回一个HystrixCommand示例，在那个方法里执行参数的具体调用逻辑
        return new UserCommand(ids,userService);
    }

    // 这里包含的是请求返回的结果 分发请求结果 这里包含三个请求的请求结果
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection) {
        int count =0;
        for (CollapsedRequest<User,Integer> request:collection) {
            request.setResponse(users.get(count++));
        }
    }

}
