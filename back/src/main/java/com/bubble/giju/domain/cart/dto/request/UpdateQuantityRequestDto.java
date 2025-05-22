package com.bubble.giju.domain.cart.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateQuantityRequestDto {

    private int quantity;

    @Builder
    public UpdateQuantityRequestDto (int quantity) {
        this.quantity = quantity;
    }
}
