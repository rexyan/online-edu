package com.yanrs.edu.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanrs.edu.teacher.entity.EduVideo;
import com.yanrs.edu.teacher.mapper.EduVideoMapper;
import com.yanrs.edu.teacher.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author rex
 * @since 2020-07-07
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public void deleteVideoByCourseId(String id) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", id);
        baseMapper.delete(eduVideoQueryWrapper);
    }

    @Override
    public boolean removeVideo(String id) {
        int i = baseMapper.deleteById(id);
        // TODO 删除视频
        return i>0;
    }
}
