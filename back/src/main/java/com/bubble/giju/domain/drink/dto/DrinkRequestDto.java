package com.bubble.giju.domain.drink.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DrinkRequestDto {
    @Schema(description = "술 이름")
    private String name;
    @Schema(description = "가격")
    private int price;
    @Schema(description = "재고")
    private int stock;
    @Schema(description = "알코올 함유량(%)")
    private double alcoholContent;
    @Schema(description = "용량")
    private int volume;
    @Schema(description = "지역")
    private String region;
    @Schema(description = "카테고리 Id")
    private int categoryId;
}
