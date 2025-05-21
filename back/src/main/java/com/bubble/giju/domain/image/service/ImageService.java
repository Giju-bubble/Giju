package com.bubble.giju.domain.image.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    String uploadFile(MultipartFile file) throws IOException;
    List<String> uploadFiles(List<MultipartFile> files) throws IOException;
}
