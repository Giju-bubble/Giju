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

    public void update(UserDto.Request request) {
        this.name = request.getName() != null ? request.getName() : this.name;
        this.email = request.getEmail() != null ? request.getEmail() : this.email;
        this.phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : this.phoneNumber;
        this.birthday = request.getBirthday() != null ? request.getBirthday() : this.birthday;
    }

    @Override
    public String toString() {
        return "userId=" + userId + ", loginId=" + loginId + ", password=" + password + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber;
    }
}
