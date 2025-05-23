package com.bubble.giju.domain.address.entity;

import com.bubble.giju.domain.address.dto.AddressDto;
import com.bubble.giju.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 받는사람 정보
    private String recipientName;
    private String phoneNumber;

    private String alias;
    private boolean defaultAddress;

    // 주소 정보
    private int postcode; // 우편번호
    private String roadAddress; // 도로명주소
    private String buildingName; // 건물이름

    private String detailAddress; // 상세주소

    public void updateDefaultAddressToFalse() {
        this.defaultAddress = false;
    }

    public void updateDefaultAddressToTrue() {
        this.defaultAddress = true;
    }

    // Update 메서드
    public void update(AddressDto.Request request) {
        this.recipientName = request.getRecipientName() != null ? request.getRecipientName() : this.recipientName;
        this.phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : this.phoneNumber;
        this.alias = request.getAlias() != null ? request.getAlias() : this.alias;
        this.defaultAddress = request.isDefaultAddress();
        this.postcode = request.getPostcode() != 0 ? request.getPostcode() : this.postcode;
        this.roadAddress = request.getRoadAddress() != null ? request.getRoadAddress() : this.roadAddress;
        this.buildingName = request.getBuildingName() != null ? request.getBuildingName() : this.buildingName;
        this.detailAddress = request.getDetailAddress() != null ? request.getDetailAddress() : this.detailAddress;
    }
}
