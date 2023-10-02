package org.example.user;

import com.netflix.hystrix.*;

import java.util.List;

public class UserCommand extends HystrixCommand<List<User>> {
    private List<Integer> id;
    private UserService userService;

    public UserCommand(List<Integer> id, UserService userService) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchCmd")).
                andCommandKey(HystrixCommandKey.Factory.asKey("batchCmd")));
       this.id=id;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        List<User> all = userService.getAll(id);
        return all;
    }
}
