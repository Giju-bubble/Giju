package com.bubble.giju.domain.drink.entity;

import com.bubble.giju.domain.image.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name="Drink_images")
public class DrinkImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="drink_image_id", nullable=false, updatable=false, unique=true)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="drink_id", nullable=false)
    private Drink drink;

    @Column(name="is_thumbnail", nullable=false)
    private boolean isThumbnail;

    @Builder
    public DrinkImage(Long id,Image image, Drink drink, boolean isThumbnail) {
        this.id = id;
        this.image = image;
        this.drink = drink;
        this.isThumbnail = isThumbnail;
    }
}
