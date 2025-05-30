// src/main/java/com/bubble/giju/global/config/DeliverySoapClient.java
package com.bubble.giju.global.config;

import com.bubble.giju.domain.delivery.jaxb.AddDeliveryRequest;
import com.bubble.giju.domain.delivery.jaxb.DeliveryRequestDto;
import com.bubble.giju.domain.delivery.jaxb.DeliveryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliverySoapClient {

    private final WebServiceTemplate webServiceTemplate;

    @Value("${post.service-key}")
    private String serviceKey;

    public DeliveryResponseDto addDelivery(DeliveryRequestDto reqDto) {
        reqDto.setServiceKey(serviceKey);
        AddDeliveryRequest wrapper = new AddDeliveryRequest(reqDto);

        log.debug("▶▶▶ SOAP Request payload:\n{}", toXmlString(wrapper));

        try {
            DeliveryResponseDto resp = (DeliveryResponseDto) webServiceTemplate
                    .marshalSendAndReceive(
                            "https://biz.epost.go.kr/KpostPortal/openapi",
                            wrapper,
                            new SoapActionCallback("https://svc.webservice.epost.go.kr/addDelivery")
                    );
            log.debug("◀◀◀ SOAP Response object: {}", resp);
            return resp;
        } catch (Exception e) {
            log.error("SOAP 호출 실패", e);
            throw e;
        }
    }

    /** JAXB 객체를 XML 문자열로 직렬화해서 반환 */
    private String toXmlString(Object jaxbObject) {
        try {
            StringWriter sw = new StringWriter();
            webServiceTemplate.getMarshaller().marshal(jaxbObject, new StreamResult(sw));
            return sw.toString();
        } catch (Exception e) {
            return "(XML 직렬화 실패: " + e.getMessage() + ")";
        }
    }
}
