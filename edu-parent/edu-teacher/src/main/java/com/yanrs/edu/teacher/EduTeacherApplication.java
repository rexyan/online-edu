package com.yanrs.edu.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EduTeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduTeacherApplication.class, args);
    }
}
