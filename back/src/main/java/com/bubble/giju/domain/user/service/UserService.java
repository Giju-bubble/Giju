package com.bubble.giju.domain.user.service;

import com.bubble.giju.domain.user.dto.UserCreateRequest;

public interface UserService {
    void save(UserCreateRequest userCreateRequest);
}
