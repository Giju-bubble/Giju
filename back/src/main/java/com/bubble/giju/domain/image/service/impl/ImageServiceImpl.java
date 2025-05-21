package com.bubble.giju.domain.image.service.impl;

import com.bubble.giju.domain.image.entity.Image;
import com.bubble.giju.domain.image.repository.ImageRepository;
import com.bubble.giju.domain.image.service.S3UploadService;
import com.bubble.giju.domain.image.service.ImageService;
import com.bubble.giju.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    public final S3UploadService s3UploadService;
    public final ImageRepository imageRepository;
    
    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        File resizeFile= ImageUtils.resize(file,800,800);
        String url = s3UploadService.upload(resizeFile);
        imageRepository.save(new Image(url));

        return url;
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<File> resizeFiles = ImageUtils.resizeAll(files,800,800);
        List<String> urlList = s3UploadService.uploadAll(resizeFiles);
        for(String url : urlList){
            imageRepository.save(new Image(url));
        }

        return urlList;
    }

}
