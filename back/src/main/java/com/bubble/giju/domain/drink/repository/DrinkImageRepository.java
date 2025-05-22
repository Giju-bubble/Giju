package com.bubble.giju.domain.drink.repository;

import com.bubble.giju.domain.drink.entity.DrinkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkImageRepository extends JpaRepository<DrinkImage, Long> {
    DrinkImage findByDrinkIdAndThumbnailIsTrue(Long drinkId);
    List<DrinkImage> findByDrinkIdAndThumbnailIsFalse(Long drinkId);

}
