package com.bubble.giju.domain.address.dto;

import lombok.Getter;

public class AddressDto {

    @Getter
    public static class Request {
        private String recipientName;
        private String phoneNumber;

        private String alias;
        private boolean defaultAddress = false;

        private int postcode;
        private String roadAddress;
        private String buildingName;

        private String detailAddress;
    }


    @Getter
    public static class AddressResponse {

    }
}
