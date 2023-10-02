package org.example.config;

import com.netflix.hystrix.*;
import org.example.pojo.Product;
import org.example.pojo.User;
import org.example.service.UserService;

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

        List<Integer> ids = new ArrayList<>(collection.size());
        for (CollapsedRequest<User,Integer> userIntegerCollapsedRequest: collection) {
            ids.add(userIntegerCollapsedRequest.getArgument());
        }

        return new UserCommand(ids,userService);
    }

    /**
     * List<user> users：这是一个List<user>类型的参数，表示合并请求的响应结果。
     * 根据您的代码，它被命名为users，但是根据参数名来看，是User对象的集合。
     * 在这个方法中，您可以使用users参数中的数据来映射到对应的合并请求。根据CollapsedRequest对象的定义，
     * 它包含了合并请求的参数和响应类型等信息。您可以使用这些信息来将users中的数据分配给对应的合并请求。
     *Collection<CollapsedRequest<User, Integer>> collection：
     * 这是一个Collection<CollapsedRequest<User, Integer>>类型的参数，表示合并请求的集合。
     * 在mapResponseToRequests()方法中，您可以通过遍历collection参数来获取每个合并请求的信息。
     * 然后，您可以将users中的数据根据合适的逻辑分配给对应的合并请求。
     * 请注意，CollapsedRequest是Hystrix提供的一个类，用于封装合并请求的相关信息。在这个参数中，
     * 它被泛型化为CollapsedRequest<User, Integer>，表示合并请求的响应类型为User，合并请求的参数类型为Integer。
     * @param users
     * @param collection
     */

    // 这里包含的是请求返回的结果 分发请求结果
    @Override
    protected void mapResponseToRequests(List<User> users, Collection<CollapsedRequest<User, Integer>> collection) {
        int count =0;
        for (CollapsedRequest<User,Integer> request:collection) {
            request.setResponse(users.get(0));
            break;
        }
    }

}
