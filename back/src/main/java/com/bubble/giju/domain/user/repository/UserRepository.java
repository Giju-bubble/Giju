package com.bubble.giju.domain.user.repository;

import com.bubble.giju.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // 테스트용 추후 스프링 로그인 구현시 제거 예정
    Optional<User> findByLoginId(String loginId);

    Optional<User> findById(UUID userId);
}
