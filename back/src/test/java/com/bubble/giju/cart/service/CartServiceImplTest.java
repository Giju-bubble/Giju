package com.bubble.giju.cart.service;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.UpdateQuantityRequestDto;
import com.bubble.giju.domain.cart.dto.response.AddToCartResponseDto;
import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.cart.repository.CartRepository;
import com.bubble.giju.domain.cart.service.serviceImpl.CartServiceImpl;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.drink.repository.DrinkRepository;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
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
        int count = 2;

        // given
        AddToCartRequestDto requestDto = AddToCartRequestDto.builder()
                .drinkId(1L)
                .quantity(count)
                .build();

        when(userRepository.findByLoginId("test")).thenReturn(Optional.of(testUser));
        when(drinkRepository.findById(1L)).thenReturn(Optional.of(testDrink));
        when(cartRepository.findByUserAndDrink(testUser, testDrink)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cartRepository.findAllByUser(testUser)).thenReturn(
                List.of(Cart.builder()
                        .user(testUser)
                        .drink(testDrink)
                        .quantity(count)
                        .build())
                );

        // when
        AddToCartResponseDto response = cartService.addToCart(requestDto);

        // then
        assertThat(response.getCartItem().getDrinkId()).isEqualTo(1L);
        assertThat(response.getCartItem().getQuantity()).isEqualTo(2);
        assertThat(response.getCartItem().getTotalPrice()).isEqualTo(16000); // 8000 * 2
        assertThat(response.getCartTotalPrice()).isEqualTo(16000);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("장바구니에 이미 담긴 물건이 있을 때 수량 증가")
    void addToCart_whenItemAlreadyExists_thenIncreaseQuantity() {
        // given
        AddToCartRequestDto requestDto = AddToCartRequestDto.builder()
                .drinkId(1L)
                .quantity(4)
                .build();

        Cart existingCart = Cart.builder()
                .user(testUser)
                .drink(testDrink)
                .quantity(2) // 기존 수량
                .build();


        when(userRepository.findByLoginId("test")).thenReturn(Optional.of(testUser));
        when(drinkRepository.findById(1L)).thenReturn(Optional.of(testDrink));
        when(cartRepository.findByUserAndDrink(testUser, testDrink)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0)); //save()에 전달된 첫 번째 인자(=0번 인덱스)를 그대로 반환
        when(cartRepository.findAllByUser(testUser)).thenReturn(
                List.of(Cart.builder().user(testUser).drink(testDrink).quantity(6).build())
        );

        log.info("기존 수량: {}", existingCart.getQuantity());

        // when
        AddToCartResponseDto response = cartService.addToCart(requestDto);

        // then
        assertThat(response.getCartItem().getDrinkId()).isEqualTo(1L);
        assertThat(response.getCartItem().getQuantity()).isEqualTo(6); // 2+4
        log.info("최종 응답 수량: {}", response.getCartItem().getQuantity());
        assertThat(response.getCartItem().getTotalPrice()).isEqualTo(48000); // 8000 * 6
        assertThat(response.getCartTotalPrice()).isEqualTo(48000);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    @DisplayName("상품이 여러개 일 때, 개별 상품 값 및 모든상품 총 가격")
    void Item_uniPrice_Cart_totalPrice() {
        //given
        Drink drinkA = Drink.builder()
                .id(1L)
                .name("홍주")
                .price(15000)
                .build();

        Drink drinkB = Drink.builder()
                .id(2L)
                .name("막걸리")
                .price(7000)
                .build();

        Cart cart1 = Cart.builder().user(testUser).drink(drinkA).quantity(2).build(); // 15000 * 2 = 30000
        Cart cart2 = Cart.builder().user(testUser).drink(drinkB).quantity(1).build();


        when(userRepository.findByLoginId("test")).thenReturn(Optional.of(testUser));
        when(drinkRepository.findById(1L)).thenReturn(Optional.of(drinkA));
        when(cartRepository.findByUserAndDrink(testUser, drinkA)).thenReturn(Optional.of(cart1));
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cartRepository.findAllByUser(testUser)).thenReturn(List.of(cart1, cart2));


        // when
        AddToCartRequestDto requestDto = AddToCartRequestDto.builder()
                .drinkId(1L)
                .quantity(0)
                .build();

        AddToCartResponseDto response = cartService.addToCart(requestDto);

        // then
        assertThat(response.getCartItem().getDrinkId()).isEqualTo(1L);
        assertThat(response.getCartItem().getQuantity()).isEqualTo(2);
        assertThat(response.getCartItem().getTotalPrice()).isEqualTo(30000);
        assertThat(response.getCartTotalPrice()).isEqualTo(37000); // 30,000 + 7,000

    }

    @Test
    @DisplayName("상품수량 변경, 증가 시")
    void updateQuantityPlus () {
        //given
        Long cartId = 1L;
        int updateQuantity = 5;

        // 장바구니 임시 생성
        Cart cart = Cart.builder()
                .id(cartId)
                .user(testUser)
                .drink(testDrink)
                .quantity(1)
                .build();

        UpdateQuantityRequestDto updateQuantityRequestDto = UpdateQuantityRequestDto.builder()
                .quantity(updateQuantity)
                .build();


        List<Cart> userCartList = List.of(
                Cart.builder()
                        .id(1L)
                        .user(testUser)
                        .drink(testDrink)
                        .quantity(updateQuantity) // 변경된 수량
                        .build()
        );

        when(userRepository.findByLoginId("test")).thenReturn(Optional.of(testUser));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.findAllByUser(testUser)).thenReturn(userCartList);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);


        // when
        AddToCartResponseDto response = cartService.updateQuantity(cartId, updateQuantityRequestDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCartItem().getQuantity()).isEqualTo(5);
        assertThat(response.getCartItem().getTotalPrice()).isEqualTo(40000);
        assertThat(response.getCartTotalPrice()).isEqualTo(40000);
    }

    @Test
    @DisplayName("상품수량 변경, 감소 시")
    void updateQuantityMinus () {
        //given
        Long cartId = 1L;
        int updateQuantity = 2;

        // 장바구니 임시 생성
        Cart cart = Cart.builder()
                .id(cartId)
                .user(testUser)
                .drink(testDrink)
                .quantity(5)
                .build();

        UpdateQuantityRequestDto updateQuantityRequestDto = UpdateQuantityRequestDto.builder()
                .quantity(updateQuantity)
                .build();


        List<Cart> userCartList = List.of(
                Cart.builder()
                        .id(1L)
                        .user(testUser)
                        .drink(testDrink)
                        .quantity(updateQuantity) // 변경된 수량
                        .build()
        );

        when(userRepository.findByLoginId("test")).thenReturn(Optional.of(testUser));
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(cartRepository.findAllByUser(testUser)).thenReturn(userCartList);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);


        // when
        AddToCartResponseDto response = cartService.updateQuantity(cartId, updateQuantityRequestDto);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCartItem().getQuantity()).isEqualTo(2);
        assertThat(response.getCartItem().getTotalPrice()).isEqualTo(16000);
        assertThat(response.getCartTotalPrice()).isEqualTo(16000);
    }
}
