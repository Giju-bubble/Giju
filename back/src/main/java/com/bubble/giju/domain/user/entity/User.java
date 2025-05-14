package com.bubble.giju.domain.user.entity;

import com.bubble.giju.domain.user.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;

    String loginId;
    String password;
    String name;
    Date birthday;
    Date createdAt;
    Role role;
}
