package com.bubble.giju.domain.address.dto;

import lombok.Getter;

public class AddressDto {

    @Getter
    public static class Request {
        boolean defaultAddress = false;
    }


    @Getter
    public static class AddressResponse {

    }
}
