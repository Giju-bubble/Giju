package com.bubble.giju.domain.user.controller;

import com.bubble.giju.domain.user.service.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("ADMIN")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminUserController {

    private final UserAdminService userAdminService;

    @GetMapping("/users")
    public void getUsers(@RequestParam(required = false, defaultValue = "0") int page,
                         @RequestParam(required = false, defaultValue = "0") int size,
                         Pageable pageable) {
        pageable = PageRequest.of(page, size);
        userAdminService.getAllUsers(pageable);
    }

    @GetMapping("/users/{userId}")
    public void getUser(@PathVariable String userId) {

    }
}
