package com.yanrs.edu.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EduRegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduRegisterApplication.class, args);
    }
}
