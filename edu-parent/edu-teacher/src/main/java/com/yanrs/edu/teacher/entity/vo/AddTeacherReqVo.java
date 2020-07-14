package com.yanrs.edu.teacher.entity.vo;

import lombok.Data;

@Data
public class AddTeacherReqVo {
    private String name;
    private String intro;
    private Integer level;
    private String career;
    private String avatar;
    private Integer sort;
    private Boolean isDeleted;
}
