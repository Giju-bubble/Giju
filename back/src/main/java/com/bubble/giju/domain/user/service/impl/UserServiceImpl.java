package com.bubble.giju.domain.user.service.impl;

import com.bubble.giju.domain.user.dto.UserCreateRequest;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.enums.Role;
import com.bubble.giju.domain.user.repository.UserRepository;
import com.bubble.giju.domain.user.service.UserService;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserCreateRequest userCreateRequest) {
        if (userRepository.findByLoginId(userCreateRequest.getLoginId()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_LoginId);
        }

        User user = User.builder()
                .loginId(userCreateRequest.getLoginId())
                .password(bCryptPasswordEncoder.encode(userCreateRequest.getPassword()))
                .name(userCreateRequest.getName())
                .email(userCreateRequest.getEmail())
                .phoneNumber(userCreateRequest.getPhoneNumber())
                .birthday(userCreateRequest.getBirthDay())
                .role(Role.valueOf(userCreateRequest.getRole().toUpperCase()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Override
    public void findByLoginId(String loginId) {
        Optional<User> byLoginId = userRepository.findByLoginId(loginId);
    }
}
