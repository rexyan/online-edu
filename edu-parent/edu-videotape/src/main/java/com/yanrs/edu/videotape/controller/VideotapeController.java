package com.yanrs.edu.videotape.controller;

import com.yanrs.edu.common.R;
import com.yanrs.edu.videotape.service.VideotapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("edu/videotape")
public class VideotapeController {

    @Autowired
    private VideotapeService videotapeService;

    /***
     * 上传视频
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R uploadVideoToAli(@RequestParam("file") MultipartFile file){
        String videoId = videotapeService.uploadVideoToAli(file);
        return R.success().data("videoId", videoId);
    }

    /**
     * 删除视频
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R uploadVideoToAli(@PathVariable("id") String id){
        Boolean status = videotapeService.deleteVideoById(id);
        return status?R.success():R.error();
    }
}
