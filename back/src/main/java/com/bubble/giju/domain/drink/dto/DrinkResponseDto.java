package com.bubble.giju.domain.drink.dto;

import com.bubble.giju.domain.category.dto.CategoryResponseDto;
import com.bubble.giju.domain.category.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DrinkResponseDto {
    @Schema(description = "술 Id")
    private Long id;
    @Schema(description = "술 이름")
    private String name;
    @Schema(description = "술 가격")
    private int price;
    @Schema(description = "재고")
    private int stock;
    @Schema(description = "알코올 함유량(%)")
    private double alcoholContent;
    @Schema(description = "용량")
    private int volume;
    @Schema(description = "상품 삭제 여부")
    private boolean is_delete;
    @Schema(description = "지역")
    private String region;
    @Schema(description = "카테고리")
    private CategoryResponseDto category;
    @Schema(description = "대표 사진 url")
    private String thumbnailUrl;
    @Schema(description = "홍보 사진들 url")
    private List<String> drinkImageUrlList;
}
