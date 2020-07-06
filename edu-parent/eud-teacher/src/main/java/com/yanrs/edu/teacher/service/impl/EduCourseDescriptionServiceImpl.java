package com.yanrs.edu.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanrs.edu.teacher.entity.EduCourseDescription;
import com.yanrs.edu.teacher.mapper.EduCourseDescriptionMapper;
import com.yanrs.edu.teacher.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author rex
 * @since 2020-07-02
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void deleteDescriptionByCourseId(String id) {
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("id", id);
        baseMapper.delete(eduCourseDescriptionQueryWrapper);
    }
}
