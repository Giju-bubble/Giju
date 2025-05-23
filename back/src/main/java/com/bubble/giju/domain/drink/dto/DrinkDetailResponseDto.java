package com.bubble.giju.domain.drink.dto;

import com.bubble.giju.domain.category.dto.CategoryResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DrinkDetailResponseDto {

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

    @Schema(description = "리뷰 점수")
    private double reviewScore;

    @Schema(description = "리뷰 개수")
    private long reviewCount;

    @Schema(description = "찜 체크 여부")
    private boolean is_like;

    public static DrinkDetailResponseDto from(
            DrinkResponseDto dto,
            double reviewScore,
            long reviewCount,
            boolean isLike
    ) {
        return DrinkDetailResponseDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .alcoholContent(dto.getAlcoholContent())
                .volume(dto.getVolume())
                .is_delete(dto.is_delete())
                .region(dto.getRegion())
                .category(new CategoryResponseDto(dto.getCategory().getId(), dto.getCategory().getName()))
                .thumbnailUrl(dto.getThumbnailUrl())
                .drinkImageUrlList(dto.getDrinkImageUrlList())
                .reviewScore(reviewScore)
                .reviewCount(reviewCount)
                .is_like(isLike)
                .build();
    }


}
