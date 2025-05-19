package com.bubble.giju.domain.user.service;

import com.bubble.giju.domain.user.dto.UserCreateRequest;
import com.bubble.giju.domain.user.dto.UserDto;

public interface UserService {
    void save(UserCreateRequest userCreateRequest);
    UserDto.Response find(String userId);
    void update(String userId, UserDto.Request request);
}
