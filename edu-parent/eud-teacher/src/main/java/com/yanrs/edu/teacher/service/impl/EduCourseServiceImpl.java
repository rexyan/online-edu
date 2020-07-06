package com.yanrs.edu.teacher.service.impl;

import com.yanrs.edu.teacher.entity.EduCourse;
import com.yanrs.edu.teacher.entity.EduCourseDescription;
import com.yanrs.edu.teacher.entity.vo.AddCourseReqVo;
import com.yanrs.edu.teacher.handler.EduException;
import com.yanrs.edu.teacher.mapper.EduCourseMapper;
import com.yanrs.edu.teacher.service.EduChapterService;
import com.yanrs.edu.teacher.service.EduCourseDescriptionService;
import com.yanrs.edu.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanrs.edu.teacher.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yanrs.edu.common.ResponseCode.*;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author rex
 * @since 2020-07-02
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public String saveCourseInfo(AddCourseReqVo addCourseReqVo) {
        // 新增课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(addCourseReqVo, eduCourse);
        int i = baseMapper.insert(eduCourse);

        // 新增课程描述
        if(i>0){
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            eduCourseDescription.setId(eduCourse.getId());
            eduCourseDescription.setDescription(addCourseReqVo.getDescription());
            boolean b = eduCourseDescriptionService.save(eduCourseDescription);
            if (b){
                return eduCourse.getId();
            }else {
                throw new EduException(DB_INSERT_ERROR.getCode(), DB_INSERT_ERROR.getMessage());
            }
        }else{
            throw new EduException(DB_INSERT_ERROR.getCode(), DB_INSERT_ERROR.getMessage());
        }
    }

    @Override
    public AddCourseReqVo getCourseInfo(String id) {
        AddCourseReqVo CourseInfo = new AddCourseReqVo();
        // 查询课程基本信息
        EduCourse eduCourse = baseMapper.selectById(id);
        if(eduCourse!= null){
            BeanUtils.copyProperties(eduCourse, CourseInfo);
        }else{
            throw new EduException(SEARCH_RESULT_IS_NULL.getCode(), SEARCH_RESULT_IS_NULL.getMessage());
        }
        // 查询课程描述
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        CourseInfo.setDescription(eduCourseDescription.getDescription());
        return CourseInfo;
    }

    @Override
    public Boolean updateCourseInfo(String id, AddCourseReqVo addCourseReqVo) {
        EduCourse eduCourse = new EduCourse();
        addCourseReqVo.setId(id);
        BeanUtils.copyProperties(addCourseReqVo, eduCourse);
        // 修改课程基本信息
        int i = baseMapper.updateById(eduCourse);
        if(i == 0){
            throw new EduException(DB_UPDATE_ERROR.getCode(), DB_UPDATE_ERROR.getMessage());
        }

        // 修改课程描述信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(addCourseReqVo.getDescription());
        eduCourseDescription.setId(addCourseReqVo.getId());
        return eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public Boolean deleteCourseById(String id) {
        // 根据课程 ID 删除课程章节
        eduChapterService.deleteChapterByCourseId(id);
        // 根据课程 ID 删除课程小节
        eduVideoService.deleteVideoByCourseId(id);
        // 根据课程 ID 删除课程描述
        eduCourseDescriptionService.deleteDescriptionByCourseId(id);
        // 删除课程本身
        int i = baseMapper.deleteById(id);
        return i>0;
    }
}
