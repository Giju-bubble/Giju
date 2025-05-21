package com.bubble.giju.domain.address.entity;

import com.bubble.giju.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Lazy;

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

    private String alias;
    private boolean defaultAddress;

    // 주소
    // 주소 정보
    private String street;
    private String address;
    private String zipCode;

    // 정보
    private String name;
    private String phoneNumber;
    private String requestAddress;

    public void updateDefaultAddressToFalse() {
        this.defaultAddress = false;
    }
}
