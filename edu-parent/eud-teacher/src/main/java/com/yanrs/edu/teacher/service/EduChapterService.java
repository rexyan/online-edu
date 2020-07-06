package com.yanrs.edu.teacher.service;

import com.yanrs.edu.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author rex
 * @since 2020-07-07
 */
public interface EduChapterService extends IService<EduChapter> {

    void deleteChapterByCourseId(String id);
}
