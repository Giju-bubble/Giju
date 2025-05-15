package com.bubble.giju.cart.service;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.response.CartItemResponseDto;
import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.cart.repository.CartRepository;
import com.bubble.giju.domain.cart.service.CartService;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.drink.repository.DrinkRepository;
import com.bubble.giju.domain.user.entity.User;

import com.bubble.giju.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@Transactional
@SpringBootTest //실제 DB 연결
public class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private CartRepository cartRepository;

    private User testUser;
    private Drink testDrink;

    @BeforeEach
    void setUp() {
        testUser = userRepository.findByLoginId("test")
                .orElseThrow(() -> new IllegalStateException("Test 유저가 DB에 없습니다."));

        // 테스트용 술 생성
        testDrink = drinkRepository.save(Drink.builder()
                .name("막걸리")
                .price(8000)
                .build());
    }

    @Test
    @DisplayName("장바구니 생성 또는 수량증가 성공")
    void AddtoCartTest() {
        // given
        AddToCartRequestDto requestDto = AddToCartRequestDto.builder()
                .drinkId(testDrink.getId())
                .quantity(2)
                .build();

        // when
        CartItemResponseDto response = cartService.addToCart(requestDto);

        // then
        assertThat(response.getDrinkId()).isEqualTo(testDrink.getId());
        assertThat(response.getQuantity()).isEqualTo(2);

        // verify cart is in DB
        Cart savedCart = cartRepository.findByUserAndDrink(testUser, testDrink)
                .orElseThrow();
        assertThat(savedCart.getQuantity()).isEqualTo(2);
        log.info("저장된 장바구니 ID: {}", savedCart.getId());
        log.info("저장된 장바구니 수량: {}", savedCart.getQuantity());
        log.info("저장된 장바구니 사용자 ID: {}", savedCart.getUser().getUserId());
        log.info("저장된 장바구니 술 이름: {}", savedCart.getDrink().getName());
    }
}
