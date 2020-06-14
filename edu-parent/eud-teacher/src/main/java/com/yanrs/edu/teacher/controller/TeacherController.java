package com.yanrs.edu.teacher.controller;


import com.yanrs.edu.teacher.entity.Teacher;
import com.yanrs.edu.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author rex
 * @since 2020-06-14
 */
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {
    // 注入 TeacherService
    @Autowired
    private TeacherService teacherService;

    // 查询所有讲师
    @GetMapping
    public List<Teacher> getAllTeacher() {
        return teacherService.list(null);
    }
}

