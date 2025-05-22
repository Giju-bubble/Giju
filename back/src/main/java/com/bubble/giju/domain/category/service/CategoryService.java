package com.bubble.giju.domain.category.service;

import com.bubble.giju.domain.category.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();
}
