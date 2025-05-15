package com.bubble.giju.cart.service;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.response.CartItemResponseDto;
import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.cart.repository.CartRepository;
import com.bubble.giju.domain.cart.service.serviceImpl.CartServiceImpl;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.drink.repository.DrinkRepository;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Mock 사용
public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    private User testUser;
    private Drink testDrink;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .loginId("test")
                .password("pass1234")
                .name("테스트 유저")
                .email("test@bubble.com")
                .birthday(LocalDate.of(2000,1,1))
                .phoneNumber("01012345678")
                .build();

        testDrink = Drink.builder()
                .id(1L)
                .name("막걸리")
                .price(8000)
                .build();
    }

    @Test
    @DisplayName("장바구니 생성, 새로운 물건을 담을 때")
    void newItemAddToCart() {
        // given
        AddToCartRequestDto requestDto = AddToCartRequestDto.builder()
                .drinkId(1L)
                .quantity(2)
                .build();

        when(userRepository.findByLoginId("test")).thenReturn(Optional.of(testUser));
        when(drinkRepository.findById(1L)).thenReturn(Optional.of(testDrink));
        when(cartRepository.findByUserAndDrink(testUser, testDrink)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        CartItemResponseDto response = cartService.addToCart(requestDto);

        // then
        assertThat(response.getDrinkId()).isEqualTo(1L);
        assertThat(response.getQuantity()).isEqualTo(2);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("장바구니에 이미 담긴 물건이 있을 때 수량 증가")
    void addToCart_whenItemAlreadyExists_thenIncreaseQuantity() {
        // given
        AddToCartRequestDto requestDto = AddToCartRequestDto.builder()
                .drinkId(1L)
                .quantity(1)
                .build();

        Cart existingCart = Cart.builder()
                .user(testUser)
                .drink(testDrink)
                .quantity(1) // 기존 수량
                .build();

        when(userRepository.findByLoginId("test")).thenReturn(Optional.of(testUser));
        when(drinkRepository.findById(1L)).thenReturn(Optional.of(testDrink));
        when(cartRepository.findByUserAndDrink(testUser, testDrink)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0)); //save()에 전달된 첫 번째 인자(=0번 인덱스)를 그대로 반환

        // when
        CartItemResponseDto response = cartService.addToCart(requestDto);

        // then
        assertThat(response.getDrinkId()).isEqualTo(1L);
        assertThat(response.getQuantity()).isEqualTo(2); // 1 + 1
        verify(cartRepository).save(any(Cart.class));
    }
}
