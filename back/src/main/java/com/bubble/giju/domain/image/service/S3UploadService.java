package com.bubble.giju.domain.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3UploadService {
    String upload(MultipartFile file) throws IOException;
}
