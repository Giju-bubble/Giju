package com.bubble.giju.domain.user.service.impl;

import com.bubble.giju.domain.user.dto.UserCreateRequest;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.enums.Role;
import com.bubble.giju.domain.user.repository.UserRepository;
import com.bubble.giju.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(UserCreateRequest userCreateRequest) {
        User user = User.builder()
                .loginId(userCreateRequest.getLoginId())
                .password(userCreateRequest.getPassword())
                .name(userCreateRequest.getName())
                .email(userCreateRequest.getEmail())
                .phoneNumber(userCreateRequest.getPhoneNumber())
                .birthday(userCreateRequest.getBirthDay())
                .role(Role.valueOf(userCreateRequest.getRole().toUpperCase()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}
