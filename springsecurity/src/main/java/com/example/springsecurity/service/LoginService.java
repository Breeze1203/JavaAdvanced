package com.example.springsecurity.service;

import com.example.springsecurity.util.StatusUtil;

public interface LoginService {
    StatusUtil Login(String username,String password);
}
