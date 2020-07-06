package com.yanrs.edu.teacher.service;

import com.yanrs.edu.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanrs.edu.teacher.entity.vo.AddCourseReqVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author rex
 * @since 2020-07-02
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(AddCourseReqVo addCourseReqVo);

    AddCourseReqVo getCourseInfo(String id);

    Boolean updateCourseInfo(String id, AddCourseReqVo addCourseReqVo);

    Boolean deleteCourseById(String id);
}
