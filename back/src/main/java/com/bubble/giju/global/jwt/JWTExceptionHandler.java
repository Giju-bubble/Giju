package com.bubble.giju.global.jwt;

import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import com.bubble.giju.global.config.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JWTExceptionHandler extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            // CustomException 처리
            setErrorResponse(response, e.getErrorCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String timeStamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getMsg(),
                errorCode.getStatus().value(),
                timeStamp
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
