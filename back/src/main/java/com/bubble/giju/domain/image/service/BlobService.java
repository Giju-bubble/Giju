package com.bubble.giju.domain.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface BlobService {
    String upload(MultipartFile file);
}
