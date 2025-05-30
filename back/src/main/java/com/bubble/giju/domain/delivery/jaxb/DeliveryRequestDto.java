// src/main/java/com/bubble/giju/domain/delivery/jaxb/DeliveryRequestDto.java
package com.bubble.giju.domain.delivery.jaxb;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryRequest")
public class DeliveryRequestDto {
    @XmlElement(required = true)           private String serviceKey;
    @XmlElement(required = true)           private String reqstNo;
    @XmlElement(required = true)           private String agreeYn;
    // 신청자 정보
    @XmlElement(required = true)           private String applcntNm;
    @XmlElement(required = true)           private String applcntZipNo;
    @XmlElement(required = true)           private String applcntAdresNm;
    @XmlElement(required = true)           private String applcntDetailAdresNm;
    @XmlElement(required = true)           private String applcntTelno1;
    @XmlElement(required = true)           private String applcntTelno2;
    @XmlElement(required = true)           private String applcntTelno3;
    @XmlElement(required = true)           private String applcntMbtlnum1;
    @XmlElement(required = true)           private String applcntMbtlnum2;
    @XmlElement(required = true)           private String applcntMbtlnum3;
    // 수령자 정보
    @XmlElement(required = true)           private String recptr;
    @XmlElement(required = true)           private String recptrZipNo;
    @XmlElement(required = true)           private String recptrAdresNm;
    @XmlElement(required = true)           private String recptrDetailAdresNm;
    @XmlElement(required = true)           private String recptrTelno1;
    @XmlElement(required = true)           private String recptrTelno2;
    @XmlElement(required = true)           private String recptrTelno3;
    @XmlElement(required = true)           private String recptrMbtlnum1;
    @XmlElement(required = true)           private String recptrMbtlnum2;
    @XmlElement(required = true)           private String recptrMbtlnum3;
    // 물품 정보
    @XmlElement(required = true)           private String wtUn;
    @XmlElement(required = true)           private String vlUn;
    @XmlElement(required = true)           private String cnNm;
    @XmlElement(required = true)           private String cnCd;
    // 기타
    private String dlvyPartclrMatter;
    private String email;
    private String visitRceptPstofc;
    @XmlElement(required = true)           private String chrgeBndAt;
    private String rceptResultRecptnAt;
    // 필수 추가
    @XmlElement(required = true)           private String hopeRceptDe;
    @XmlElement(required = true)           private String visitRceptDe;
    // 라벨 (옵션)
    private String lblAt;
    private String lblNm;
    private String lblZipNo;
    private String lblAdresNm;
    private String lblDetailAdresNm;
    private String lblTelno1;
    private String lblTelno2;
    private String lblTelno3;
    private String lblMbtlnum1;
    private String lblMbtlnum2;
    private String lblMbtlnum3;
    // 시스템/관리자 (옵션)
    private String userId;
    private String wishReceiptTimeInterval;
    private String pickMan;
    private String pickArea;
    private String pickParty;
}
