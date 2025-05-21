package com.bubble.giju.domain.category.controller;

import com.bubble.giju.domain.category.dto.CategoryResponseDto;
import com.bubble.giju.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    public final CategoryService categoryService;

    //개수가 많지않아 페이징 처리 안함
    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categoryResponseDtos = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDtos);
    }
}
