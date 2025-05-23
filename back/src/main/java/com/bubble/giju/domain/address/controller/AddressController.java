package com.bubble.giju.domain.address.controller;

import com.bubble.giju.domain.address.dto.AddressDto;
import com.bubble.giju.domain.address.service.AddressService;
import com.bubble.giju.domain.user.dto.CustomPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/address")
@RestController
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "회원의 모든 주소가져오기", description = "회원의 모든 주소를 가져옵니다.")
    @GetMapping("/")
    public void getAddress(@AuthenticationPrincipal CustomPrincipal customPrincipal, @RequestBody AddressDto.Request request) {
        addressService.createAddress(customPrincipal.getUserId(), request);
    }


}
