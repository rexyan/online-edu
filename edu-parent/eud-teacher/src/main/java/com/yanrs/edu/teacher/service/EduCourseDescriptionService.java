package com.yanrs.edu.teacher.service;

import com.yanrs.edu.teacher.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author rex
 * @since 2020-07-02
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void deleteDescriptionByCourseId(String id);
}
