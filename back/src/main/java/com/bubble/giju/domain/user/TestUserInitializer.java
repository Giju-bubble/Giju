package com.bubble.giju.domain.user;

import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.enums.Role;
import com.bubble.giju.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestUserInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.findByLoginId("test").isEmpty()) {
            User testUser = User.builder()
                    .loginId("test")
                    .password("test1234")
                    .name("테스트유저")
                    .email("test@giju.com")
                    .phoneNumber("01012345678")
                    .birthday(LocalDate.of(2000, 1, 1))
                    .createdAt(LocalDateTime.now())
                    .role(Role.USER)
                    .build();

            userRepository.save(testUser);
        }
    }
}
