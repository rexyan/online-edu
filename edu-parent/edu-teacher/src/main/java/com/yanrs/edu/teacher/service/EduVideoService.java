package com.yanrs.edu.teacher.service;

import com.yanrs.edu.teacher.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author rex
 * @since 2020-07-07
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideoByCourseId(String id);

    boolean removeVideo(String id);
}
