package com.pt.mvc.controller;

import com.pt.mvc.annotation.Controller;
import com.pt.mvc.annotation.RequestMapping;
import com.pt.mvc.http.RequestMethod;

@Controller
@RequestMapping(path = "/index")
public class IndexController {

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public void test(String name) {

    }

    @RequestMapping(path = "/test2", method = RequestMethod.POST)
    public void test2(String name2) {

    }

    public void test3(String name3) {

    }

}