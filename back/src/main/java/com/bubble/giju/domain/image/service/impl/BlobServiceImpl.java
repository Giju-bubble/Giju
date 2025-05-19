package com.bubble.giju.domain.image.service.impl;

import com.bubble.giju.domain.image.service.BlobService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
@RequiredArgsConstructor
public class BlobServiceImpl implements BlobService {

    private final WebClient vercelBlobClient;

    @Override
    public String upload(MultipartFile file) {

        return vercelBlobClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/blob")
                        .queryParam("filename", file.getOriginalFilename())
                        .queryParam("access", "public")
                        .build())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", file.getResource()))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.get("url").asText())
                .block();
    }
}
