package com.bubble.giju.global.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiResponse <T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;
    private final HttpStatus status;

    private ApiResponse(boolean success, String message, HttpStatus status, T data) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }


    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, HttpStatus.OK, data);
    }
}