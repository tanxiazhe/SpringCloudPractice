package com.maomao2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients // 开启spring cloud feign的支持
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaCustomer2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaCustomer2Application.class, args);
    }
}
