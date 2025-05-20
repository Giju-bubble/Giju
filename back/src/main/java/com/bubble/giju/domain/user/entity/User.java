package com.bubble.giju.domain.user.entity;

import com.bubble.giju.domain.user.dto.UserDto;
import com.bubble.giju.domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID userId;

    @Column(unique = true)
    String loginId;
    String password;
    String name;
    String email;
    String phoneNumber;
    LocalDate birthday;
    LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    Role role;

    public User update(UserDto.Request request) {
        this.name = request != null ? request.getName() : this.name;
        this.email = request != null ? request.getEmail() : this.email;
        this.phoneNumber = request != null ? request.getPhoneNumber() : this.phoneNumber;
        this.birthday = request != null ? request.getBirthday() : this.birthday;
    }
}
