package com.bubble.giju.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class CategoryResponseDto {
    private int id;
    private String name;
}
