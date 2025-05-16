package com.bubble.giju.domain.cart.service.serviceImpl;

import com.bubble.giju.domain.cart.dto.request.AddToCartRequestDto;
import com.bubble.giju.domain.cart.dto.request.UpdateQuantityRequestDto;
import com.bubble.giju.domain.cart.dto.response.AddToCartResponseDto;
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


import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final DrinkRepository drinkRepository;
    private final UserRepository userRepository;

    @Override
    public AddToCartResponseDto addToCart(AddToCartRequestDto requestDto) {

        // todo  추후 CustomPrincipal 로그인한 유저 정보 조회 변경
        /*User user = getUserByPrincipal(principal);
        private User getUserByPrincipal(CustomPrincipal principal) {
            return userRepository.findById(UUID.fromString(principal.getUserId()))
                    .orElseThrow(() -> new CustomException(ErrorCode.NON_EXISTENT_USER));
        }*/
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

        int cartTotalPrice = calculateCartTotalPrice(user);

        CartItemResponseDto cartItemDto = toCartItemDto(cart);


        return AddToCartResponseDto.builder()
                .cartItem(cartItemDto)
                .cartTotalPrice(cartTotalPrice)
                .build();
    }

    @Override
    public AddToCartResponseDto updateQuantity(Long id, UpdateQuantityRequestDto updateQuantityRequestDto) {

        //추후 변경예정
        User user = userRepository.findByLoginId("test")
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NON_EXISTENT_CART));

        // 상품 수량 1개 이하가 될수 없게 막음
        if (updateQuantityRequestDto.getQuantity() < 1){
            throw new CustomException(ErrorCode.INVALID_QUANTITY);
        }

        cart.updateQuantity(updateQuantityRequestDto.getQuantity());
        cartRepository.save(cart);

        int cartTotalPrice = calculateCartTotalPrice(user);

        CartItemResponseDto cartItemDto = toCartItemDto(cart);


        return AddToCartResponseDto.builder()
                .cartItem(cartItemDto)
                .cartTotalPrice(cartTotalPrice)
                .build();
    }

    // 유저의 모든 상품 총값
    private int calculateCartTotalPrice(User user) {
        return cartRepository.findAllByUser(user).stream()
                .mapToInt(c -> c.getDrink().getPrice() * c.getQuantity())
                .sum();
    }

    // 각 상품의 값
    private CartItemResponseDto toCartItemDto(Cart cart) {
        Drink drink = cart.getDrink();
        return CartItemResponseDto.builder()
                .cartId(cart.getId())
                .drinkId(drink.getId())
                .quantity(cart.getQuantity())
                .unitPrice(drink.getPrice())
                .totalPrice(drink.getPrice() * cart.getQuantity())
                .build();
    }

    @Override
    public void deleteCartItem(List<Long> cartId) {
        //추후 변경예정
        User user = userRepository.findByLoginId("test")
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        // 유저정보 + 장바구니id  -> 장바구니 객체 찾기
        List<Cart> cart = cartRepository.findAllByUserAndIdIn(user, cartId);
        cartRepository.deleteAll(cart);
    }
}
