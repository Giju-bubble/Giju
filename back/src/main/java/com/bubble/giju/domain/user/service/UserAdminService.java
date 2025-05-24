package com.bubble.giju.domain.user.service;

import com.bubble.giju.domain.user.dto.UserDto;
import com.bubble.giju.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAdminService {
    Page<UserDto.Response> getAllUsers(Pageable pageable);
}
