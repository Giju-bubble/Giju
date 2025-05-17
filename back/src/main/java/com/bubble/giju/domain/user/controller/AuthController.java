package com.bubble.giju.domain.user.controller;

import com.bubble.giju.domain.user.dto.UserCreateRequest;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public String createUser(@RequestBody UserCreateRequest userCreateRequest) {
        System.out.println(userCreateRequest);

        userService.save(userCreateRequest);

        return "success";
    }
}
