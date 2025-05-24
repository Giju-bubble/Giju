package com.bubble.giju.domain.user.repository;

import com.bubble.giju.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLoginId(String loginId);
    Optional<User> findById(UUID userId);
    Page<User> findAll(Pageable pageable);
    boolean existsByLoginId(String loginId);

}
