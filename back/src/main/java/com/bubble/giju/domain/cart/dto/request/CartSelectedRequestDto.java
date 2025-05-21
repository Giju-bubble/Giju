package com.bubble.giju.domain.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartSelectedRequestDto {

    // 사용자가 선택한 장바구니 ID 목록
    private List<Long> cartIds;
}
