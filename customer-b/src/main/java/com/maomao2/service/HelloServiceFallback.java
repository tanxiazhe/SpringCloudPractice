package com.maomao2.service;

public class HelloServiceFallback implements HelloService {
    @Override
    public String hello() {

        return "request error";
    }
}
