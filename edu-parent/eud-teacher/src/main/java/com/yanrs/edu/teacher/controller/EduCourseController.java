package com.yanrs.edu.teacher.controller;


import com.yanrs.edu.common.R;
import com.yanrs.edu.teacher.entity.EduCourse;
import com.yanrs.edu.teacher.entity.vo.AddCourseReqVo;
import com.yanrs.edu.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author rex
 * @since 2020-07-02
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    /**
     * 添加课程信息
     * @param addCourseReqVo
     * @return
     */
    @PostMapping
    public R saveCourseInfo(@RequestBody AddCourseReqVo addCourseReqVo){
        String courseId = eduCourseService.saveCourseInfo(addCourseReqVo);
        return R.success().data("courseId", courseId);
    }

    /**
     * 查询课程详情信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getCourseInfo(@PathVariable("id") String id){
        AddCourseReqVo courseInfo = eduCourseService.getCourseInfo(id);
        return R.success().data("courseInfo", courseInfo);
    }

    /**
     * 修改课程信息
     * @param id
     * @param addCourseReqVo
     * @return
     */
    @PutMapping("/{id}")
    public R updateCourseInfo(@PathVariable("id") String id, @RequestBody AddCourseReqVo addCourseReqVo){
        Boolean update = eduCourseService.updateCourseInfo(id, addCourseReqVo);
        if (update) {
            return R.success();
        } else {
            return R.error();
        }
    }

    /**
     * 查询所有课程信息
     * @return
     */
    @GetMapping
    public R getAllCourseInfo(){
        // TODO 分页，多条件查询
        List<EduCourse> courseList = eduCourseService.list(null);
        return R.success().data("courseList", courseList);
    }

    @DeleteMapping("/{id}")
    public R deleteCourseById(@PathVariable("id") String id){
        Boolean a = eduCourseService.deleteCourseById(id);
        return a?R.success():R.error();
    }
}

