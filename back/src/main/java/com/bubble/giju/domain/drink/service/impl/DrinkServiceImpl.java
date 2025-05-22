package com.bubble.giju.domain.drink.service.impl;

import com.bubble.giju.domain.category.dto.CategoryResponseDto;
import com.bubble.giju.domain.category.entity.Category;
import com.bubble.giju.domain.category.repository.CategoryRepository;
import com.bubble.giju.domain.drink.dto.DrinkRequestDto;
import com.bubble.giju.domain.drink.dto.DrinkResponseDto;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.drink.entity.DrinkImage;
import com.bubble.giju.domain.drink.repository.DrinkImageRepository;
import com.bubble.giju.domain.drink.repository.DrinkRepository;
import com.bubble.giju.domain.drink.service.DrinkService;
import com.bubble.giju.domain.image.entity.Image;
import com.bubble.giju.domain.image.repository.ImageRepository;
import com.bubble.giju.domain.image.service.ImageService;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DrinkServiceImpl implements DrinkService {
    private final ImageService imageService;
    private final DrinkRepository drinkRepository;
    private final CategoryRepository categoryRepository;
    private final DrinkImageRepository drinkImageRepository;
    private final ImageRepository imageRepository;

    //todo 예외처리 해줄 것
    @Override
    public DrinkResponseDto saveDrink(DrinkRequestDto drinkRequestDto, List<MultipartFile> files, MultipartFile thumbnail) throws IOException {
        //카테고리 가져옴
        Category category = categoryRepository.findById(drinkRequestDto.getCategoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NONEXISTENT_CATEGORY));

        String drinkName= drinkRequestDto.getName()+" "+drinkRequestDto.getAlcoholContent()+"% "+drinkRequestDto.getVolume()+"mL";

        //술 엔티티 만듦
        Drink drink =Drink.builder().name(drinkName).price(drinkRequestDto.getPrice())
                .stock(drinkRequestDto.getStock()).alcoholContent(drinkRequestDto.getAlcoholContent())
                .volume(drinkRequestDto.getVolume()).is_delete(false).region(drinkRequestDto.getRegion())
                .category(category).build();
        //썸네일 이미지 생성 및 이미지 저장
        String thumbnailUrl=imageService.uploadFile(thumbnail);
        //todo 이거 안전하게 레파지토리에서 가져오는지? 아니면 그냥 사용해도되는지? 이대로면 예외처리도 추가해야함.
        Image thumbnailImage= imageRepository.findByUrl(thumbnailUrl);

        //썸네일 술_이미지 엔티티생성
        List<DrinkImage> drinkImageList = new ArrayList<>();
        DrinkImage thumbnailDrinkImage = DrinkImage.builder().drink(drink).image(thumbnailImage).isThumbnail(true).build();
        drinkImageList.add(thumbnailDrinkImage);

        //다른 이미지들 저장 및 url 반환받음
        List<String> drinkImageUrlList = imageService.uploadFiles(files);
        //다른 이미지들 술_이미지 테이블에 저장
        for(String drinkImageUrl:drinkImageUrlList){
            //todo N+1 문제 와 함께 예외처리
            Image image = imageRepository.findByUrl(drinkImageUrl);
            DrinkImage drinkImage= DrinkImage.builder().drink(drink).image(image).isThumbnail(false).build();
            drinkImageList.add(drinkImage);
        }
        drinkRepository.save(drink);
        drinkImageRepository.saveAll(drinkImageList);

        DrinkResponseDto drinkResponseDto = DrinkResponseDto.builder().id(drink.getId())
                .name(drink.getName()).price(drink.getPrice()).stock(drink.getStock())
                .alcoholContent(drink.getAlcoholContent()).volume(drink.getVolume())
                .is_delete(drink.is_delete()).region(drink.getRegion()).category(new CategoryResponseDto(drink.getCategory().getId(),drink.getCategory().getName()))
                .thumbnailUrl(thumbnailUrl).drinkImageUrlList(drinkImageUrlList)
                .build();
        return drinkResponseDto;
    }

    //todo  @Where + @SQLDelete로 자동 처리??
    @Override
    public DrinkResponseDto deleteDrink(Long drinkId) {
        return updateDrinkDeleteStatus(drinkId, true);
    }

    @Override
    public DrinkResponseDto restoreDrink(Long drinkId) {
        return updateDrinkDeleteStatus(drinkId, false);
    }

    private DrinkResponseDto updateDrinkDeleteStatus(Long drinkId, boolean isDeleted) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new CustomException(ErrorCode.NON_EXISTENT_DRINK));

        drink.updateDelete(isDeleted);
        drinkRepository.save(drink);

        return buildDrinkResponseDto(drink);
    }

    private DrinkResponseDto buildDrinkResponseDto(Drink drink) {
        DrinkImage thumbnailDrinkImage = drinkImageRepository.findByDrinkIdAndThumbnailIsTrue(drink.getId());
        String thumbnailUrl = thumbnailDrinkImage.getImage().getUrl();

        List<DrinkImage> drinkImageList = drinkImageRepository.findByDrinkIdAndThumbnailIsFalse(drink.getId());
        List<String> imageList = drinkImageList.stream()
                .map(img -> img.getImage().getUrl())
                .toList();

        return DrinkResponseDto.builder()
                .id(drink.getId())
                .name(drink.getName())
                .price(drink.getPrice())
                .stock(drink.getStock())
                .alcoholContent(drink.getAlcoholContent())
                .volume(drink.getVolume())
                .is_delete(drink.is_delete())
                .region(drink.getRegion())
                .category(new CategoryResponseDto(drink.getCategory().getId(),drink.getCategory().getName()))
                .thumbnailUrl(thumbnailUrl)
                .drinkImageUrlList(imageList)
                .build();
    }

}
