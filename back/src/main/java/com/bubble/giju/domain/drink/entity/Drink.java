package com.bubble.giju.domain.drink.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table ( name = "Drink")
public class Drink {

    @Id
    @GeneratedValue
    @Column(name = "drink_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Builder
    public Drink (String name,int price){
        this.name = name;
        this.price = price;
    }
}
