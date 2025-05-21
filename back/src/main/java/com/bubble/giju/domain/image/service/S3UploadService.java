package com.bubble.giju.domain.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface S3UploadService {
    String upload(File file) throws IOException;
    List<String> uploadAll(List<File> files) throws IOException;
}
