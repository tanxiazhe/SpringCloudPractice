package com.maomao2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maomao2.service.HelloService;

@RestController
public class HelloController {

    @Autowired
    HelloService service;

    @RequestMapping("/ribbon-consumer")
    public String coutomerA() {
        return service.helloService();
    }

}