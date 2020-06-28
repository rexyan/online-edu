package com.yanrs.edu.teacher.controller;


import com.yanrs.edu.common.R;
import com.yanrs.edu.teacher.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author rex
 * @since 2020-06-28
 */
@RestController
@RequestMapping("/teacher/subject")
public class EduSubjectController {
    @Autowired
    EduSubjectService eduSubjectService;


    @PostMapping("/import")
    public R importSubjectByExcel(@RequestParam("file") MultipartFile file){
        eduSubjectService.importSubject(file);
        return R.success().data("status", true);
    }
}

