package com.yanrs.edu.teacher.mapper;

import com.yanrs.edu.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanrs.edu.teacher.entity.vo.GetCoursePublishRespVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author rex
 * @since 2020-07-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    GetCoursePublishRespVo getCoursePublishInfo(String courseId);

    Boolean publishCourseInfo(String courseId);
}
