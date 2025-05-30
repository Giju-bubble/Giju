// src/main/java/com/bubble/giju/domain/delivery/jaxb/DeliveryResponseDto.java
package com.bubble.giju.domain.delivery.jaxb;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "DeliveryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliveryResponseDto {
    private String agreeYn;
    private String applcntNm;
    private String applcntZipNo;
    private String applcntAdresNm;
    private String applcntDetailAdresNm;
    private String applcntTelno1;
    private String applcntTelno2;
    private String applcntTelno3;
    private String applcntMbtlnum1;
    private String applcntMbtlnum2;
    private String applcntMbtlnum3;
    private String recptr;
    private String recptrZipNo;
    private String recptrAdresNm;
    private String recptrDetailAdresNm;
    private String recptrTelno1;
    private String recptrTelno2;
    private String recptrTelno3;
    private String recptrMbtlnum1;
    private String recptrMbtlnum2;
    private String recptrMbtlnum3;
    private String wtUn;
    private String vlUn;
    private String hopeRceptDe;
    private String visitRceptDe;
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
    private String userId;
    private String wishReceiptTimeInterval;
    private String pickMan;
    private String pickArea;
    private String pickParty;
    private String wishReceiptTime;
    private String cnNm;
    private String cnCd;
    private String dlvyPartclrMatter;
    private String email;
    private String visitRceptPstofc;
    private String chrgeBndAt;
    private String rceptResultRecptnAt;
}
