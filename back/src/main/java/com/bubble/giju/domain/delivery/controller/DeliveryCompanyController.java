package com.bubble.giju.domain.delivery.controller;

import com.bubble.giju.domain.delivery.dto.DeliveryCompanyResponseDto;
import com.bubble.giju.domain.delivery.entity.DeliveryCompany;
import com.bubble.giju.domain.delivery.service.DeliveryCompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "택배 회사관련 API")
public class DeliveryCompanyController {
    private final DeliveryCompanyService deliveryCompanyService;

    @GetMapping("/api/admin/DeliveryCompanies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DeliveryCompanyResponseDto>> getAll() {
        List<DeliveryCompanyResponseDto> deliveryCompanyResponseDtoList = deliveryCompanyService.findAll();
        return ResponseEntity.ok(deliveryCompanyResponseDtoList);
    }

}
