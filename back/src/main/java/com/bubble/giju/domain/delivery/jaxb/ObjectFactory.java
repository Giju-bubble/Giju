package com.bubble.giju.domain.delivery.jaxb;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {}

    public DeliveryRequestDto createDeliveryRequestDto() {
        return new DeliveryRequestDto();
    }

    public DeliveryResponseDto createDeliveryResponseDto() {
        return new DeliveryResponseDto();
    }

    public Envelope createEnvelope() {
        return new Envelope();
    }

    public Body createBody() {
        return new Body();
    }
}