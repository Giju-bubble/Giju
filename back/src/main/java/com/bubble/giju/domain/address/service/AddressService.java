package com.bubble.giju.domain.address.service;

import com.bubble.giju.domain.address.dto.AddressDto;
import com.bubble.giju.domain.address.entity.Address;

import java.util.List;

public interface AddressService {
    void createAddress(String userId, AddressDto.Request request);
    List<Address> getAddress(String userId);
    Long deleteAddress(String userId, Long addressId);
}
