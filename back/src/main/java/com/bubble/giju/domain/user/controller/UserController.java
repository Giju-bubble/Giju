package com.bubble.giju.domain.user.controller;

import com.bubble.giju.domain.user.dto.CustomPrincipal;
import com.bubble.giju.domain.user.dto.UserDto;
import com.bubble.giju.domain.user.service.UserService;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 관련 API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    // read
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<UserDto.Response>> getUser(@AuthenticationPrincipal CustomPrincipal customPrincipal) {
        UserDto.Response response = userService.find(customPrincipal.getUserId());
        ApiResponse<UserDto.Response> apiResponse = ApiResponse.success("회원불러오기 성공", response);

        return ResponseEntity.ok(apiResponse);
    }

    // update
    @PatchMapping("/users")
    public ResponseEntity<ApiResponse<UserDto.Response>> updateUser(@AuthenticationPrincipal CustomPrincipal customPrincipal, @RequestBody UserDto.Request request) {
        log.info(request.toString());

        ApiResponse<UserDto.Response> apiResponse = ApiResponse.success(
                "회원수정 성공", userService.update(customPrincipal.getUserId(), request));

        return ResponseEntity.ok(apiResponse);
    }

    // delete
    @DeleteMapping("/users")
    public ResponseEntity<ApiResponse<String>> deleteUser(@AuthenticationPrincipal CustomPrincipal customPrincipal) {
        ApiResponse<String> apiResponse = ApiResponse.success(
                "회원삭제 성공", userService.delete(customPrincipal.getUserId()));

        return ResponseEntity.ok(apiResponse);
    }
}
