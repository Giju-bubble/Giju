package com.bubble.giju.domain.user.entity;

import com.bubble.giju.domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;

    String loginId;
    String password;
    String name;
    String email;
    String phoneNumber;
    LocalDate birthday;
    LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    Role role;
}
