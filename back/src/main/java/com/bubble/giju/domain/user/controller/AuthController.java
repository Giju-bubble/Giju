package com.bubble.giju.domain.user.controller;

import com.bubble.giju.domain.user.dto.UserCreateRequest;
import com.bubble.giju.domain.user.dto.UserDto;
import com.bubble.giju.domain.user.service.UserService;
import com.bubble.giju.global.config.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto.Response>> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        System.out.println(userCreateRequest);

        UserDto.Response response = userService.save(userCreateRequest);

        ApiResponse<UserDto.Response> apiResponse = ApiResponse.success("회원가입 성공", response);
        return ResponseEntity.ok(apiResponse);
    }
}
