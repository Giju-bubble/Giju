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
    @Column(name = "user_id")
    UUID userId;


    @Column(name = "login_id", unique = true)
    String loginId;

    @Column(name = "password")
    String password;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "birthday")
    LocalDate birthday;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    public String getStringUserID() {
        return userId.toString();
    }

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
