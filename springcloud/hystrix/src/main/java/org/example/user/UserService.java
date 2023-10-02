package org.example.service;

import org.example.pojo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("UserService")
public class UserService {
    public List<User> getAll(List<Integer> id){
        List<User> users = new ArrayList<>();
        for (Integer i:id) {
            User user = new User(i, "李白", i);
            users.add(user);
        }
        return users;
    }
}
