package com.bubble.giju.domain.delivery.controller;

import com.bubble.giju.domain.delivery.dto.DeliveryCompanyResponseDto;
import com.bubble.giju.domain.delivery.entity.DeliveryCompany;
import com.bubble.giju.domain.delivery.service.DeliveryCompanyService;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "택배 회사관련 API")
public class DeliveryCompanyController {
    private final DeliveryCompanyService deliveryCompanyService;

    @GetMapping("/api/delivery-companies")
    public ResponseEntity<List<DeliveryCompanyResponseDto>> getAll() {
        List<DeliveryCompanyResponseDto> deliveryCompanyResponseDtoList = deliveryCompanyService.findAll();
        return ResponseEntity.ok(deliveryCompanyResponseDtoList);
    }

    @DeleteMapping("/api/admin/delivery-company/{delivery-company-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DeliveryCompanyResponseDto>> deleteById(@PathVariable("delivery-company-id") int deliveryCompanyId) {
        DeliveryCompanyResponseDto deliveryCompanyResponseDto = deliveryCompanyService.deleteById(deliveryCompanyId);
        ApiResponse<DeliveryCompanyResponseDto> apiResponse= ApiResponse.success("택배회사 삭제에 성공했습니다.", deliveryCompanyResponseDto);
        return ResponseEntity.ok(apiResponse);
    }

}
