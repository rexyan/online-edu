package com.yanrs.edu.teacher.controller;


import com.yanrs.edu.common.R;
import com.yanrs.edu.teacher.entity.EduVideo;
import com.yanrs.edu.teacher.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author rex
 * @since 2020-07-07
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService eduVideoService;

    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @PostMapping
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);
        return save?R.success():R.error();
    }

    /**
     * 根据 ID 进行查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getVideo(@PathVariable("id") String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.success().data("eduVideo", eduVideo);
    }

    /**
     * 修改
     * @param id
     * @param eduVideo
     * @return
     */
    @PutMapping("/{id}")
    public R updateVideo(@PathVariable("id") String id, @RequestBody EduVideo eduVideo){
        eduVideo.setId(id);
        boolean b = eduVideoService.updateById(eduVideo);
        return b?R.success():R.error();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteVideo(@PathVariable("id") String id){
        boolean b = eduVideoService.removeVideo(id);
        return b?R.success():R.error();
    }
}

