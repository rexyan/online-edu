package com.yanrs.edu.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanrs.edu.teacher.entity.Teacher;
import com.yanrs.edu.teacher.entity.vo.TeacherListReqVo;
import com.yanrs.edu.teacher.mapper.TeacherMapper;
import com.yanrs.edu.teacher.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author rex
 * @since 2020-06-14
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public void getTeacherListByCondition(Page<Teacher> teacherPage, TeacherListReqVo teacherListReqVo) {
        if(teacherListReqVo==null){
            baseMapper.selectPage(teacherPage, null);
        }else{
            String name = teacherListReqVo.getName();
            String level = teacherListReqVo.getLevel();
            String startTime = teacherListReqVo.getStartTime();
            String endTime = teacherListReqVo.getEndTime();

            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(name)){
                queryWrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)){
                queryWrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(startTime)){
                queryWrapper.gt("gmt_create", startTime);
            }
            if (!StringUtils.isEmpty(endTime)){
                queryWrapper.gt("gmt_modified", endTime);
            }
            baseMapper.selectPage(teacherPage, queryWrapper);
        }
    }
}
