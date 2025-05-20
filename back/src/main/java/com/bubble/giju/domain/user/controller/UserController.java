package com.bubble.giju.domain.user.controller;

import com.bubble.giju.domain.user.dto.CustomPrincipal;
import com.bubble.giju.domain.user.dto.UserDto;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.service.UserService;
import com.bubble.giju.global.config.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController("/api")
public class UserController {

    private final UserService userService;

    // read
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getUser(@AuthenticationPrincipal CustomPrincipal customPrincipal) {
        UserDto.Response response = userService.find(customPrincipal.getUserId());
        ApiResponse<UserDto.Response> apiResponse = ApiResponse.success("회원가입 성공", response);

        return ResponseEntity.ok(apiResponse);
    }

    // update
    @PatchMapping("/users")
    public ResponseEntity<ApiResponse> updateUser(@AuthenticationPrincipal CustomPrincipal customPrincipal, @RequestBody UserDto.Request request) {
        log.info(request.toString());

        ApiResponse<UserDto.Response> apiResponse = ApiResponse.success(
                "회원수정 성공", userService.update(customPrincipal.getUserId(), request));

        return ResponseEntity.ok(apiResponse);
    }

}
