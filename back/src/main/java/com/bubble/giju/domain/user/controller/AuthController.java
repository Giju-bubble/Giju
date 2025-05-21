package com.bubble.giju.domain.user.controller;

import com.bubble.giju.domain.user.dto.CustomPrincipal;
import com.bubble.giju.domain.user.dto.LoginDto;
import com.bubble.giju.domain.user.dto.UserCreateRequest;
import com.bubble.giju.domain.user.dto.UserDto;
import com.bubble.giju.domain.user.service.UserService;
import com.bubble.giju.global.config.ApiResponse;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import com.bubble.giju.global.jwt.CookieUtil;
import com.bubble.giju.global.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto.Response>> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        System.out.println(userCreateRequest);

        UserDto.Response response = userService.save(userCreateRequest);

        ApiResponse<UserDto.Response> apiResponse = ApiResponse.success("회원가입 성공", response);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginDto.LoginResponse>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh != null) {
            throw new CustomException(ErrorCode.INVALID_JWT);
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            throw new CustomException(ErrorCode.INVALID_JWT);
        }

        String role = jwtUtil.getRole(refresh);
        String username = jwtUtil.getUsername(refresh);
        String userId = jwtUtil.getUserId(refresh);

        String newAccessToken = jwtUtil.createAccessToken(username, role, userId);
        String newRefreshToken = jwtUtil.createRefreshToken(username, role, userId);

        LoginDto.LoginResponse loginResponse = LoginDto.LoginResponse.of(newAccessToken, newRefreshToken);
        ApiResponse<LoginDto.LoginResponse> apiResponse = ApiResponse.success(newAccessToken, loginResponse);

        // test
        response.setHeader("access", newAccessToken);
        response.addCookie(cookieUtil.createCookie("refresh", newRefreshToken));

        return ResponseEntity.ok(apiResponse);
    }
}
