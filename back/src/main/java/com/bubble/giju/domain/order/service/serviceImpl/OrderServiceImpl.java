package com.bubble.giju.domain.order.service.serviceImpl;

import com.bubble.giju.domain.cart.entity.Cart;
import com.bubble.giju.domain.cart.repository.CartRepository;
import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.order.dto.request.OrderRequestDto;
import com.bubble.giju.domain.order.entity.Order;
import com.bubble.giju.domain.order.entity.OrderDetail;
import com.bubble.giju.domain.order.repository.OrderRepository;
import com.bubble.giju.domain.order.service.OrderService;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    @Transactional
    public Order createOrder(String userId, List<Long> cartItemIds) {
        User user = userRepository.findByLoginId("test")
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        List<Cart> cartItems = cartRepository.findAllById(cartItemIds);

        // 총 금액 계산
        int totalAmount = calculateTotalAmount(cartItems);

        // 배달비
        int deliveryCharge = calculateDeliveryCharge(totalAmount);

        // order 생성
        Order order = Order.builder()
                .totalAmount(totalAmount)
                .deliveryCharge(deliveryCharge)
                .user(user)
                .build();

        for (Cart cart : cartItems) {
            Drink drink = cart.getDrink();
            int price = drink.getPrice();
            int quantity = cart.getQuantity();

            // orderDetail 생성
            OrderDetail orderDetail = OrderDetail.builder()
                    .drinkName(drink.getName())
                    .price(price)
                    .quantity(quantity)
                    .order(order)
                    .build();
            //양뱡향 설정
            order.addOrderDetail(orderDetail);
        }
        return orderRepository.save(order);
    }

    // 선택된 물 건 총값 계산
    private int calculateTotalAmount(List<Cart> cartItems) {
        return cartItems.stream()
                .mapToInt(Cart::getSubtotal)
                .sum();
    }


    // 3만원 이상 물건 구매시 배달비 무료!
    @Value("${order.delivery-charge}")
    private int deliveryCharge;

    private int calculateDeliveryCharge(int totalAmount) {
        return totalAmount >= 30000 ? 0 : deliveryCharge;
    }
}
