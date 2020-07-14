package com.yanrs.edu.teacher.entity.vo;

import lombok.Data;

/**
 * 封装课程发布详细信息
 */
@Data
public class GetCoursePublishRespVo {
    private String id;  // id
    private String title;  // 课程名称
    private String cover;  // 课程封面
    private String price;  // 课程价格
    private String description;  // 课程描述
    private String teacherName;  // 讲师名称
    private String levelOne;  // 一级分类名称
    private String levelTwo;  // 二级分类名称
}
