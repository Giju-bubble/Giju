package com.bubble.giju.domain.user.repository;

import com.bubble.giju.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
