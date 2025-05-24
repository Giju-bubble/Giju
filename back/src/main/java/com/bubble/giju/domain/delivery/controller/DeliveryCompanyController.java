package com.bubble.giju.domain.delivery.controller;

import com.bubble.giju.domain.delivery.dto.DeliveryCompanyResponseDto;
import com.bubble.giju.domain.delivery.dto.DeliveryCompanyUpdateRequestDto;
import com.bubble.giju.domain.delivery.entity.DeliveryCompany;
import com.bubble.giju.domain.delivery.service.DeliveryCompanyService;
import com.bubble.giju.global.config.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "택배 회사관련 API")
public class DeliveryCompanyController {
    private final DeliveryCompanyService deliveryCompanyService;

    @Operation(summary = "택배회사 조회",description = "택배 회사 리스트로 조회 API")
    @GetMapping("/api/delivery-companies")
    public ResponseEntity<List<DeliveryCompanyResponseDto>> getAll() {
        List<DeliveryCompanyResponseDto> deliveryCompanyResponseDtoList = deliveryCompanyService.findAll();
        return ResponseEntity.ok(deliveryCompanyResponseDtoList);
    }

    @Operation(summary = "택배회사 데이터 삭제",description = "택배 회사 데이터 삭제 API")
    @DeleteMapping("/api/admin/delivery-company/{delivery-company-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DeliveryCompanyResponseDto>> deleteById(@PathVariable("delivery-company-id") int deliveryCompanyId) {
        DeliveryCompanyResponseDto deliveryCompanyResponseDto = deliveryCompanyService.deleteById(deliveryCompanyId);
        ApiResponse<DeliveryCompanyResponseDto> apiResponse= ApiResponse.success("택배회사 삭제에 성공했습니다.", deliveryCompanyResponseDto);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "택배회사 데이터 수정",description = "택배 회사 데이터 수정 API")
    @PutMapping("/api/admin/delivery-company/{delivery-company-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DeliveryCompanyResponseDto>> deleteById(@PathVariable("delivery-company-id") int deliveryCompanyId,
                                                                              @RequestBody DeliveryCompanyUpdateRequestDto deliveryCompanyUpdateRequestDto) {
        DeliveryCompanyResponseDto deliveryCompanyResponseDto = deliveryCompanyService.update(deliveryCompanyId,deliveryCompanyUpdateRequestDto);
        ApiResponse<DeliveryCompanyResponseDto> apiResponse= ApiResponse.success("택배회사 수정에 성공했습니다.", deliveryCompanyResponseDto);
        return ResponseEntity.ok(apiResponse);
    }


}
