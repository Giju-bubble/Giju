package com.bubble.giju.domain.drink.controller;

import com.bubble.giju.domain.drink.dto.DrinkRequestDto;
import com.bubble.giju.domain.drink.dto.DrinkResponseDto;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.drink.service.DrinkService;
import com.bubble.giju.global.config.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    //todo admin 관련 api가 존재하지않아 임시로 만듦 Value 어노테이션 통해서 검증도 해야함.
    //todo ApiResponse 성공은 모두 200 처리? 근데 객체안에 들어있긴함.
    @PostMapping(value = "/api/admin/drink", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DrinkResponseDto>> addDrink(
            @RequestPart("drink") DrinkRequestDto drinkDto,   // JSON 데이터를 part로 받음
            @RequestPart("files") List<MultipartFile> files,  // 여러 파일
            @RequestPart("thumbnail") MultipartFile thumbnail // 단일 파일
            ) throws IOException {

        DrinkResponseDto drinkResponseDto = drinkService.saveDrink(drinkDto,files,thumbnail);
        ApiResponse<DrinkResponseDto> apiResponse=ApiResponse.success("술 상품 등록에 성공하셨습니다.",drinkResponseDto);
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }
}
