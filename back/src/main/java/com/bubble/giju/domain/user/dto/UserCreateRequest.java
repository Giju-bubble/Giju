package com.bubble.giju.domain.user.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserCreateRequest {
    String loginId;
    String password;
    String name;
    Date birthday;
    String role;
}
