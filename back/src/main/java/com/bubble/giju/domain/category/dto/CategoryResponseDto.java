package com.bubble.giju.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class CategoryResponseDto {
    @Schema(description = "카테고리 ID")
    private int id;
    @Schema(description = "카테고리 이름")
    private String name;
}
