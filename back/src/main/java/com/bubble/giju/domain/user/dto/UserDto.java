package com.bubble.giju.domain.user.dto;

import com.bubble.giju.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
public class UserDto {

    @Getter
    public static class Request {
        String userId;
        String name;
        String email;
        String phoneNumber;
        LocalDate birthday;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        String userId;
        String loginId;
        String name;
        String email;
        String phoneNumber;
        LocalDate birthday;
        LocalDateTime createdAt;
        String role;

        public static Response fromEntity(User user) {
            return new Response(
                    user.getUserId().toString(),
                    user.getLoginId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getBirthday(),
                    user.getCreatedAt(),
                    user.getRole().toString()
            );
        }

    }

}
