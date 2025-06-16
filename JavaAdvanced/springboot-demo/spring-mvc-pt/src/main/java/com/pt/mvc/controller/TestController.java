package com.pt.mvc.controller;

import com.pt.mvc.annotation.RequestMapping;
import com.pt.mvc.http.RequestMethod;
import org.springframework.stereotype.Service;

@Service
public class TestController {

    @RequestMapping(path = "/test4", method = RequestMethod.POST)
    public void test4(String name2) {

    }

}