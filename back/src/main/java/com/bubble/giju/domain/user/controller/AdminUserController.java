package com.bubble.giju.domain.user.controller;

import com.bubble.giju.domain.user.dto.UserDto;
import com.bubble.giju.domain.user.service.UserAdminService;
import com.bubble.giju.domain.user.service.UserService;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin - User", description = "관리자 - 사용자 관련 API")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminUserController {

    private final UserAdminService userAdminService;
    private final UserService userService;

    @Operation(summary = "모든 사용자 아이디 리스트를 불러옵니다.", description = "UUID userID 리스트입니다, 가져올 페이지번호와 갯수를 입력합니다.")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getUsers(@RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<String> allUserIds = userAdminService.getAllUsers(pageable);
        ApiResponse<Page<String>> apiResponse = ApiResponse.success("전체 유저ID 목록입니다.", allUserIds);

         return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "특정 사용자정보를 불러옵니다.", description = "사용자 정보 전체를 가져옵니다.")
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<UserDto.Response>> getUser(@PathVariable String userId) {
        UserDto.Response response = userService.find(userId);

        ApiResponse<UserDto.Response> apiResponse = ApiResponse.success("Admin - 회원불러오기 성공", response);

        return ResponseEntity.ok(apiResponse);
    }
}
