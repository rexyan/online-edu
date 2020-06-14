package com.yanrs.edu.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanrs.edu.common.R;
import com.yanrs.edu.teacher.entity.Teacher;
import com.yanrs.edu.teacher.entity.vo.TeacherListReqVo;
import com.yanrs.edu.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R getAllTeacher() {
        List<Teacher> teacherList = teacherService.list(null);
        return R.success().data("item", teacherList);
    }

    // 删除讲师
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable("id") String id) {
        boolean removeById = teacherService.removeById(id);
        if (removeById){
            return R.success();
        }else{
            return R.fail();
        }
    }

    // 分页查询讲师列表
    @GetMapping("/list/{currentPage}/{pageSize}")
    public R getPageTeacher(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize) {
        Page<Teacher> teacherPage = new Page<>(currentPage, pageSize);
        teacherService.page(teacherPage, null);
        return R.success().data("total", teacherPage.getTotal()).data("items", teacherPage.getRecords());
    }

    // 分页多条件查询讲师列表
    @GetMapping("/condition/{currentPage}/{pageSize}")
    public R getConditionPageTeacher(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize, TeacherListReqVo teacherListReqVo) {
        Page<Teacher> teacherPage = new Page<>(currentPage, pageSize);
        teacherService.getTeacherListByCondition(teacherPage, teacherListReqVo);
        return R.success().data("total", teacherPage.getTotal()).data("items", teacherPage.getRecords());
    }
}

