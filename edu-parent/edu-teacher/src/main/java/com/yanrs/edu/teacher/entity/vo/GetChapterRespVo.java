package com.yanrs.edu.teacher.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetChapterRespVo {
    private String id;
    private String title;
    private List<GetVideoRespVo> videos = new ArrayList<>();
}
