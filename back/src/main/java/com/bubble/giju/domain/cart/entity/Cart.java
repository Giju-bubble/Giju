package com.bubble.giju.domain.cart.entity;

import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "cart_id")
    private long id;

    @Column (name = "quantity" , nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id", referencedColumnName = "drink_id")
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Builder
    public Cart(int quantity, Drink drink, User user) {
        this.quantity = quantity;
        this.drink = drink;
        this.user = user;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

}
