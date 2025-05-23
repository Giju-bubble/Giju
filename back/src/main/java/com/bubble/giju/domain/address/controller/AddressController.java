package com.bubble.giju.domain.address.controller;

import com.bubble.giju.domain.address.dto.AddressDto;
import com.bubble.giju.domain.address.service.AddressService;
import com.bubble.giju.domain.user.dto.CustomPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주소 API", description = "주소 관련 API 입니다.")
@RequiredArgsConstructor
@RequestMapping("/api/address")
@RestController
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "주소 저장", description = "회원의 주소를 저장합니다.")
    @PostMapping("/")
    public void getAddress(@AuthenticationPrincipal CustomPrincipal customPrincipal, @RequestBody AddressDto.Request request) {
        addressService.createAddress(customPrincipal.getUserId(), request);
    }




}
