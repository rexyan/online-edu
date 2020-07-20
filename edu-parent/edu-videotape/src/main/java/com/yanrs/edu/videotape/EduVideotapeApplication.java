package com.yanrs.edu.videotape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EduVideotapeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduVideotapeApplication.class, args);
    }
}
