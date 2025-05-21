package com.bubble.giju.domain.user.dto;

import lombok.Getter;

public class LoginDto {
    @Getter
    public static class LoginRequest {
        private String loginId;
        private String password;
    }

    @Getter
    public static class LoginResponse {
        private String access;
        private String refresh;

        public static LoginResponse of(String accessToken, String refreshToken) {
            LoginResponse response = new LoginResponse();
            response.access = accessToken;
            response.refresh = refreshToken;

            return response;
        }
    }
}
