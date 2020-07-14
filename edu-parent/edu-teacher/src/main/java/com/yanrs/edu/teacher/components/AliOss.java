package com.yanrs.edu.teacher.components;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

@Component
public class AliOss {
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    @Value("${aliyun.oss.file.foldername}")
    private String folderName;

    public String UploadFile(String fileName, InputStream fileInputStream){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
        // 生成随机文件名, 并上传到日期文件夹下面
        String uploadFileName = new DateTime().toString("yyyy/MM/dd") + "/" + UUID.randomUUID().toString().replace("-", "") + "_" + fileName;
        // 上传文件流。
        ossClient.putObject(bucketName, uploadFileName, fileInputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return "https://" + bucketName + "." + endpoint + "/" +  uploadFileName;
    }
}
