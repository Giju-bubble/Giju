// src/main/java/com/bubble/giju/domain/delivery/jaxb/AddDeliveryRequest.java
package com.bubble.giju.domain.delivery.jaxb;

import jakarta.xml.bind.annotation.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@XmlRootElement(name = "addDelivery")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class AddDeliveryRequest {
    @XmlElement(name = "DeliveryRequest", required = true)
    private DeliveryRequestDto deliveryRequest;

    public AddDeliveryRequest(DeliveryRequestDto dto) {
        this.deliveryRequest = dto;
    }
}
