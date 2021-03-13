package com.justmop.jwtdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping({ "/hello" })
    public String hello() {
        return "Hello JWT Token";
    }

    @RequestMapping({ "/bye" })
    public String selam() {
        return "Bye JWT Token";
    }
}
