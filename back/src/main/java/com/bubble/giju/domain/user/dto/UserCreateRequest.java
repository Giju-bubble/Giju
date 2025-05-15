package com.bubble.giju.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class UserCreateRequest {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate birthDay;
    private String role;
}