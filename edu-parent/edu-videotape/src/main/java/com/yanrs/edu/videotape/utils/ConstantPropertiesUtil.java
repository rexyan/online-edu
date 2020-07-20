package com.yanrs.edu.videotape.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {
    // 获取配置文件中值
    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    // 获取配置文件中值
    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    // 定义两个常量
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Override
    public void afterPropertiesSet() {
        // 将获取的配置文件中的值赋予常量
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
