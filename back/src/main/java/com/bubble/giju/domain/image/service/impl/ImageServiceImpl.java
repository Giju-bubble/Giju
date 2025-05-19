package com.bubble.giju.domain.image.service.impl;

import com.bubble.giju.domain.image.entity.Image;
import com.bubble.giju.domain.image.repository.ImageRepository;
import com.bubble.giju.domain.image.service.BlobService;
import com.bubble.giju.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    public final BlobService blobService;
    public final ImageRepository imageRepository;
    
    @Override
    public String upload(MultipartFile file) {
        String url = blobService.upload(file);
        imageRepository.save(new Image(url));

        return url;
    }
}
