package com.bubble.giju.domain.like.entity;

import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drink_id",nullable = false)
    private Drink drink;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name = "is_delete")
    private boolean delete;

    @Builder
    public Like(Drink drink, User user, boolean delete) {
        this.drink = drink;
        this.user = user;
        this.delete = delete;
    }
}
