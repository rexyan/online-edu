package com.yanrs.edu.videotape.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.yanrs.edu.videotape.service.VideotapeService;
import com.yanrs.edu.videotape.utils.AliyunVodSDKUtils;
import com.yanrs.edu.videotape.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VideotapeServiceImpl implements VideotapeService {


    @Override
    public String uploadVideoToAli(MultipartFile file) {
        String videoId = null;
        // 获取文件完整名称
        String originalFilename = file.getOriginalFilename();
        // 截取文件名称，去除后缀
        String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        try {
            InputStream inputStream = file.getInputStream();
            // 以下代码参考阿里云 SDK 中 UploadVideoDemo 的流式上传接口
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, originalFilename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            if (response.isSuccess()) {
                // 获取上传的视频 ID
                videoId = response.getVideoId();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return videoId;
        }
        return videoId;
    }

    @Override
    public Boolean deleteVideoById(String id) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
