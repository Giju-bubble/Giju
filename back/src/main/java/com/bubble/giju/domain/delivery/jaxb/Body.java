package com.bubble.giju.domain.delivery.jaxb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Body")
public class Body {
    @JacksonXmlProperty(localName = "addDeliveryResponse")
    private DeliveryResponseDto deliveryResponse;
}