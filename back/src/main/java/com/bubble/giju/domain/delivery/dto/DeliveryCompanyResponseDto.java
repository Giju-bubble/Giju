package com.bubble.giju.domain.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryCompanyResponseDto {
    @Schema(name = "택배 회사 아이디",description = "택배 회사 아이디")
    private int deliveryCompanyId;
    @Schema(name = "택배 회사 이름",description = "택배 회사 이름")
    private String deliveryCompanyName;

}
