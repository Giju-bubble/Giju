package com.bubble.giju.domain.drink.repository;

import com.bubble.giju.domain.drink.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
}
