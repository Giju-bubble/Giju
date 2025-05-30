package com.bubble.giju.global.config;

import jakarta.xml.soap.MessageFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import org.springframework.ws.client.support.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;


@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller deliveryMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.bubble.giju.domain.delivery.jaxb");
        return marshaller;
    }

    @Bean
    public SaajSoapMessageFactory messageFactory() throws Exception {
        SaajSoapMessageFactory factory = new SaajSoapMessageFactory(MessageFactory.newInstance());
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(
            SaajSoapMessageFactory messageFactory,
            Jaxb2Marshaller marshaller
    ) {
        WebServiceTemplate tpl = new WebServiceTemplate(messageFactory);
        tpl.setMarshaller(marshaller);
        tpl.setUnmarshaller(marshaller);

        // ▶ PayloadLoggingInterceptor 등록
        PayloadLoggingInterceptor loggingInterceptor = new PayloadLoggingInterceptor();
        // (필요시, log 레벨 조정: spring-ws.client.MessageTracingLogCategory 페이로드 로깅 로거를 DEBUG 로)
        tpl.setInterceptors(new org.springframework.ws.client.support.interceptor.ClientInterceptor[]{
                loggingInterceptor
        });

        return tpl;
    }
}
