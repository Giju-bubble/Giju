package com.bubble.giju.domain.cart.repository;

import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserAndDrink(User user, Drink drink);
    List<Cart> findAllByUser(User user);
    List<Cart> findAllByUserAndIdIn(User user, List<Long> id);

}
