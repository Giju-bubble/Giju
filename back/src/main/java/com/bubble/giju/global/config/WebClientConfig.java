package com.bubble.giju.global.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    // 타임 아웃
    private static final int TIMEOUT_SECONDS = 10;


    @Bean
    public WebClient webClient() {

        /*
        * 각각 연결,요청, 응답 TIMEOUT_SECONDS 수행 -> 이상 걸리시 실패처리
        * 서버가 너무 오래 응답을 안해주는거 방지
        * */

        // TCP 연결/응답/쓰기 타임아웃 설정
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(TIMEOUT_SECONDS, TimeUnit.SECONDS))  // 읽기 타임아웃
                        .addHandlerLast(new WriteTimeoutHandler(TIMEOUT_SECONDS, TimeUnit.SECONDS)) // 쓰기 타임아웃
                );

        // 위에서 만든 TcpClient를 기반으로 HttpClient 생성
        HttpClient httpClient = HttpClient.from(tcpClient)
                .responseTimeout(Duration.ofSeconds(TIMEOUT_SECONDS)); // 응답까지 기다릴 최대 시간


        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://api.tosspayments.com") // 공통 base URL (선택)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
