package com.bubble.giju.domain.cart.service.serviceImpl;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.response.CartItemResponseDto;
import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.cart.repository.CartRepository;
import com.bubble.giju.domain.cart.service.CartService;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.drink.repository.DrinkRepository;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.repository.UserRepository;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final DrinkRepository drinkRepository;
    private final UserRepository userRepository;

    @Override
    public CartItemResponseDto addToCart(AddToCartRequestDto requestDto) {

        // todo 로그인한 유저 정보 조회
        User user = userRepository.findByLoginId("test")
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        // 술 조회
        Drink drink = drinkRepository.findById(requestDto.getDrinkId())
                .orElseThrow(() -> new CustomException(ErrorCode.NON_EXISTENT_DRINK));

        // 기존에 담긴 장바구니에 담긴 상품인지 중복 확인
        Optional<Cart> optionalCart = cartRepository.findByUserAndDrink(user, drink);

        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
            cart.increaseQuantity(requestDto.getQuantity());
        } else {
            cart = Cart.builder()
                    .user(user)
                    .drink(drink)
                    .quantity(requestDto.getQuantity())
                    .build();
        }
        cartRepository.save(cart);

        return CartItemResponseDto.builder()
                .cartId(cart.getId())
                .drinkId(drink.getId())
                .quantity(cart.getQuantity())
                .build();
    }

}
