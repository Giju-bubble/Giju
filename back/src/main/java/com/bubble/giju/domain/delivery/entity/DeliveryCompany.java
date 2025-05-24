package com.bubble.giju.domain.delivery.entity;

import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Delivery_companies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeliveryCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_company_id", nullable = false, updatable = false, unique = true)
    private int id;

    @Column(name ="delivery_company_name",nullable = false, unique = true, length = 50)
    private String name;

    public DeliveryCompany(String name) {
        this.name = name;
    }

    public void modifyName(String newName) {
        this.name = newName;
    }


    @OneToMany(mappedBy = "deliveryCompany")
    private List<Delivery> deliveries = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        if (!deliveries.isEmpty()) {
            throw new CustomException(ErrorCode.FOREIGN_KEY_CONSTRAINT_VIOLATION);
        }
    }

}
