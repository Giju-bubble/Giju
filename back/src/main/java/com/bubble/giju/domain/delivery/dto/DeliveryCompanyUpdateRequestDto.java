package com.bubble.giju.domain.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeliveryCompanyUpdateRequestDto {
    @Schema(name = "새로운 택배회사 이름",description = "새로운 택배회사 이름")
    private String deliveryCompanyName;
}
