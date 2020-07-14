package com.yanrs.edu.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanrs.edu.teacher.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanrs.edu.teacher.entity.vo.TeacherListReqVo;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author rex
 * @since 2020-06-14
 */
public interface TeacherService extends IService<Teacher> {

    void getTeacherListByCondition(Page<Teacher> teacherPage, TeacherListReqVo teacherListReqVo);
}
