package com.bubble.giju.domain.category.service.impl;

import com.bubble.giju.domain.category.dto.CategoryRequestDto;
import com.bubble.giju.domain.category.dto.CategoryResponseDto;
import com.bubble.giju.domain.category.entity.Category;
import com.bubble.giju.domain.category.repository.CategoryRepository;
import com.bubble.giju.domain.category.service.CategoryService;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    public final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC,"id")).stream().map(
                category -> new CategoryResponseDto(category.getId(), category.getName()))
                .toList();
    }
}
