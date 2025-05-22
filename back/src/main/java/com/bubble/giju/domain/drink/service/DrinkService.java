package com.bubble.giju.domain.drink.service;

import com.bubble.giju.domain.drink.dto.DrinkDetailResponseDto;
import com.bubble.giju.domain.drink.dto.DrinkRequestDto;
import com.bubble.giju.domain.drink.dto.DrinkResponseDto;
import com.bubble.giju.domain.drink.dto.DrinkUpdateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DrinkService {
    DrinkResponseDto saveDrink(DrinkRequestDto drinkRequestDto, List<MultipartFile> files, MultipartFile thumbnail) throws IOException;
    DrinkResponseDto deleteDrink(Long drinkId);
    DrinkResponseDto restoreDrink(Long drinkId);
    DrinkResponseDto updateDrink(Long drinkId,DrinkUpdateRequestDto drinkUpdateRequestDto);
    DrinkDetailResponseDto findById(Long drinkId, UUID userId);
}
