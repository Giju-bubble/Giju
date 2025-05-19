package com.bubble.giju.domain.image.service.impl;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlobServiceImplTest {
    private MockWebServer mockWebServer;

    @Mock
    private WebClient vercelBlobClient;

    @InjectMocks
    private BlobServiceImpl blobService;

    @BeforeEach
    void setUp() throws Exception {
        // 1) MockWebServer 생성 및 시작
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Mockito가 만든 vercelBlobClient를 실제 MockWebServer URL로 재설정
        when(vercelBlobClient.post()).thenAnswer(invocation ->
                WebClient.builder()
                        .baseUrl(mockWebServer.url("/").toString())
                        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer test-token")
                        .build()
                        .post()
        );

    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void uploadReturnsUrlWhenBlobServiceSucceeds() throws Exception {
        // given: MockWebServer에 응답 큐잉
        String expectedUrl = "https://vercel.blob/my-image.png";
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"url\":\"" + expectedUrl + "\"}")
                .addHeader("Content-Type", "application/json"));

        // and: 더미 MultipartFile
        MultipartFile file = new MockMultipartFile(
                "file", "test.png", "image/png", "dummy".getBytes());

        // when
        String actual = blobService.upload(file);

        // then
        assertThat(actual).isEqualTo(expectedUrl);

        // 그리고 요청이 제대로 왔는지 확인
        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath())
                .contains("/v1/blob?filename=test.png&access=public");
        assertThat(request.getHeader("Authorization"))
                .isEqualTo("Bearer test-token");
    }
}