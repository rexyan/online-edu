package com.yanrs.edu.teacher.controller;


import com.yanrs.edu.common.R;
import com.yanrs.edu.teacher.entity.EduChapter;
import com.yanrs.edu.teacher.entity.vo.GetChapterRespVo;
import com.yanrs.edu.teacher.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author rex
 * @since 2020-07-07
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    /**
     * 根据课程 Id 查询该课程的章节和小结信息
     * @param id
     * @return
     */
    @GetMapping("/chaptervideo/{id}")
    public R getChapterVideoList(@PathVariable("id") String id){
        List<GetChapterRespVo> chapterVideoList = eduChapterService.getChapterVideoList(id);
        return R.success().data("chapterVideoList", chapterVideoList);
    }

    /**
     * 添加章节信息
     * @param eduChapter
     * @return
     */
    @PostMapping()
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        return save?R.success():R.error();
    }

    /**
     * 根据 ID 查询章节信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getChapterInfo(@PathVariable("id") String id){
        EduChapter eduChapter = eduChapterService.getById(id);
        return R.success().data("eduChapter", eduChapter);
    }

    /**
     * 更新章节信息
     * @param id
     * @param eduChapter
     * @return
     */
    @PutMapping("/{id}")
    public R updateChapterInfo(@PathVariable("id") String id, @RequestBody EduChapter eduChapter){
        eduChapter.setId(id);
        boolean b = eduChapterService.updateById(eduChapter);
        return b?R.success():R.error();
    }

    @DeleteMapping("/{id}")
    public R deleteChapterInfo(@PathVariable("id") String id){
        boolean b = eduChapterService.deleteChapterById(id);
        return b?R.success():R.error();
    }
}

