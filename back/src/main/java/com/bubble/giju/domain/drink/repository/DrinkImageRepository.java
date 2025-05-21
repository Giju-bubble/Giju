package com.bubble.giju.domain.drink.repository;

import com.bubble.giju.domain.drink.entity.DrinkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkImageRepository extends JpaRepository<DrinkImage, Long> {
}
