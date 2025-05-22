package com.bubble.giju.domain.user.controller;

import com.bubble.giju.domain.user.dto.UserDto;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.service.UserAdminService;
import com.bubble.giju.domain.user.service.UserService;
import com.bubble.giju.domain.user.service.impl.UserServiceImpl;
import com.bubble.giju.global.config.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("ADMIN")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminUserController {

    private final UserAdminService userAdminService;
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getUsers(@RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "5") int size,
                         Pageable pageable) {
        pageable = PageRequest.of(page - 1, size);

        Page<String> allUserIds = userAdminService.getAllUsers(pageable);
        ApiResponse<Page<String>> apiResponse = ApiResponse.success("전체 유저ID 목록입니다.", allUserIds);

         return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserDto.Response>> getUser(@PathVariable String userId) {
        UserDto.Response response = userService.find(userId);

        ApiResponse<UserDto.Response> apiResponse = ApiResponse.success("Admin - 회원불러오기 성공", response);

        return ResponseEntity.ok(apiResponse);
    }
}
