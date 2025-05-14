package com.bubble.giju.domain.user.service.impl;

import com.bubble.giju.domain.user.dto.UserCreateRequest;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.enums.Role;
import com.bubble.giju.domain.user.repository.UserRepository;
import com.bubble.giju.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .birthday(userCreateRequest.getBirthday())
                .role(Role.valueOf(userCreateRequest.getRole()))
                .build();

        userRepository.save(user);
    }
}
