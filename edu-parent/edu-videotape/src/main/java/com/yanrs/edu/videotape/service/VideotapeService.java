package com.yanrs.edu.videotape.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideotapeService {
    String uploadVideoToAli(MultipartFile file);

    Boolean deleteVideoById(String id);
}
