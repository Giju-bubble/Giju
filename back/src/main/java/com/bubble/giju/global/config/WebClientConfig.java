package com.bubble.giju.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient vercelBlobClient(@Value("${vercel.blob.token}") String token) {
        return WebClient.builder()
                .baseUrl("https://api.vercel.com")                     // Vercel REST API 기준 URL :contentReference[oaicite:0]{index=0}
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Bearer 토큰 방식 :contentReference[oaicite:1]{index=1}
                .build();
    }
}
