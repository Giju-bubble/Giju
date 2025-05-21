package com.bubble.giju.domain.category.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="categories")
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name="category_name",unique=true, nullable=false)
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
