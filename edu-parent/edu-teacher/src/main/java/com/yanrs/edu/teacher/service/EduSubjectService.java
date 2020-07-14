package com.yanrs.edu.teacher.service;

import com.yanrs.edu.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author rex
 * @since 2020-06-28
 */
public interface EduSubjectService extends IService<EduSubject> {
    // 读取 excel 中的内容
    void importSubject(MultipartFile file);

    Boolean deleteSubjectById(String id);

    Boolean addLevelOne(EduSubject eduSubject);

    Boolean addLevelTwo(EduSubject eduSubject);

    List getAllSubject();
}
