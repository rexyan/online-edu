package com.yanrs.edu.teacher.controller;


import com.yanrs.edu.common.R;
import com.yanrs.edu.teacher.entity.EduSubject;
import com.yanrs.edu.teacher.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author rex
 * @since 2020-06-28
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService eduSubjectService;

    /**
     * 导入 excel 信息
     * @param file
     * @return
     */
    @PostMapping("/import")
    public R importSubjectByExcel(@RequestParam("file") MultipartFile file){
        eduSubjectService.importSubject(file);
        return R.success().data("status", true);
    }

    /**
     * 删除信息（只要此 ID 不是任何记录的 parent id ，那就可以删除）
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteSubjectById(@PathVariable("id") String id){
        Boolean status = eduSubjectService.deleteSubjectById(id);
        return status? R.success():R.error();
    }

    /**
     * 查询所有分类信息 [{"一级分类",children:[二级分类]}]
     * @return
     */
    @GetMapping("/")
    public R getAllSubject(){
        List allSubject = eduSubjectService.getAllSubject();
        return R.success().data("OneSubjectDto", allSubject);
    }

    /**
     * 添加一级分类
     */
    @PostMapping("/level1")
    public R addLevelOne(@RequestBody EduSubject eduSubject){
        Boolean status = eduSubjectService.addLevelOne(eduSubject);
        return status? R.success():R.error();
    }

    /**
     * 添加二级分类
     */
    @PostMapping("/level2")
    public R addLevelTwo(@RequestBody EduSubject eduSubject){
        Boolean status = eduSubjectService.addLevelTwo(eduSubject);
        return status? R.success():R.error();
    }
}

