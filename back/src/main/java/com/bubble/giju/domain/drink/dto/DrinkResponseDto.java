package com.bubble.giju.domain.drink.dto;

import com.bubble.giju.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DrinkResponseDto {
    private Long id;

    private String name;

    private int price;

    private int stock;
    private double alcoholContent;
    private int volume;
    private boolean is_delete;
    private String region;

    private Category category;

    private String thumbnailUrl;
    private List<String> drinkImageUrlList;
}
