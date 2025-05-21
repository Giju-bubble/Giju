package com.bubble.giju.domain.drink.dto;

import com.bubble.giju.domain.category.dto.CategoryRequestDto;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrinkRequestDto {
    private String name;
    private int price;
    private int stock;
    private double alcoholContent;
    private int volume;
    private String region;
    private int categoryId;
}
