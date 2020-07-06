package com.yanrs.edu.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanrs.edu.teacher.entity.EduChapter;
import com.yanrs.edu.teacher.mapper.EduChapterMapper;
import com.yanrs.edu.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author rex
 * @since 2020-07-07
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Override
    public void deleteChapterByCourseId(String id) {
        // 根据课程 ID 删除章节
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", id);
        baseMapper.delete(eduChapterQueryWrapper);
    }
}
