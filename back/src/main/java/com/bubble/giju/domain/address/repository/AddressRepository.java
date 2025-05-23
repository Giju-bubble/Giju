package com.bubble.giju.domain.address.repository;

import com.bubble.giju.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_UserId(UUID userUserId);
    Optional<Address> findByUser_UserIdAndDefaultAddressTrue(UUID userUserId);
    Optional<Address> findByIdAndUser_UserId(Long id, UUID userUserId);
}
