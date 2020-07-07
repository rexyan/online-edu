package com.yanrs.edu.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanrs.edu.teacher.entity.EduChapter;
import com.yanrs.edu.teacher.entity.EduVideo;
import com.yanrs.edu.teacher.entity.vo.GetChapterRespVo;
import com.yanrs.edu.teacher.entity.vo.GetVideoRespVo;
import com.yanrs.edu.teacher.mapper.EduChapterMapper;
import com.yanrs.edu.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanrs.edu.teacher.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public void deleteChapterByCourseId(String id) {
        // 根据课程 ID 删除章节
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", id);
        baseMapper.delete(eduChapterQueryWrapper);
    }

    @Override
    public List<GetChapterRespVo> getChapterVideoList(String id) {
        ArrayList<GetChapterRespVo> chapterRespList = new ArrayList<>();

        // 根据课程 ID 查询章节信息
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", id);
        List<EduChapter> eduChapterList = baseMapper.selectList(eduChapterQueryWrapper);

        // 根据章节 ID 查询小节信息
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", id);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoQueryWrapper);

        // 遍历所有章节，将值复制到 GetChapterRespVo 中
        for (int i = 0; i < eduChapterList.size(); i++) {
            GetChapterRespVo getChapterRespVo = new GetChapterRespVo();
            ArrayList<GetVideoRespVo> videoRespList= new ArrayList<>();

            EduChapter eduChapter = eduChapterList.get(i);
            BeanUtils.copyProperties(eduChapter, getChapterRespVo);

            // 将小节数据放入 GetChapterRespVo 中
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    GetVideoRespVo videoRespVo = new GetVideoRespVo();

                    BeanUtils.copyProperties(eduVideo, videoRespVo);
                    videoRespList.add(videoRespVo);
                }
            }
            // 将小节集合放入章节集合中
            getChapterRespVo.setVideos(videoRespList);
            chapterRespList.add(getChapterRespVo);
        }
        return chapterRespList;
    }

    @Override
    public boolean deleteChapterById(String id) {
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_id", id);
        // 如果章节下有小节，那么就不进行删除
        int count = eduVideoService.count(eduVideoQueryWrapper);
        if(count>0){
            return false;
        }
        // 没有小节信息，进行删除
        int i = baseMapper.deleteById(id);
        return i>0;
    }
}
