package com.yanrs.edu.teacher.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.yanrs.edu.teacher.mapper")
public class EduTeacherConfig {
}
