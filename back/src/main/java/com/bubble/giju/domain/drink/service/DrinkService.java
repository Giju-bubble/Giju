package com.bubble.giju.domain.drink.service;

import com.bubble.giju.domain.drink.dto.DrinkRequestDto;
import com.bubble.giju.domain.drink.dto.DrinkResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DrinkService {
    DrinkResponseDto saveDrink(DrinkRequestDto drinkRequestDto, List<MultipartFile> files, MultipartFile thumbnail) throws IOException;
    DrinkResponseDto deleteDrink(Long drinkId);
    DrinkResponseDto restoreDrink(Long drinkId);
}
