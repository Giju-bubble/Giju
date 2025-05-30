package com.bubble.giju.domain.delivery.dto;

import lombok.Data;

/**
 * 컨트롤러에서 @RequestBody로 받는 DTO.
 * addDelivery 서비스 호출에 필요한 모든 사용자 입력값을 담습니다.
 */
@Data
public class DeliveryCreateRequestDto {
    // 1) 개인정보 동의
    private String agreeYn;               // "Y" 또는 "N"

    // 2) 신청자(발송자) 정보
    private String senderName;            // applcntNm
    private String senderZip;             // applcntZipNo
    private String senderAddress;         // applcntAdresNm
    private String senderDetailAddress;   // applcntDetailAdresNm
    private String senderTel1;            // applcntTelno1
    private String senderTel2;            // applcntTelno2
    private String senderTel3;            // applcntTelno3
    private String senderMobile1;         // applcntMbtlnum1
    private String senderMobile2;         // applcntMbtlnum2
    private String senderMobile3;         // applcntMbtlnum3

    // 3) 수령자 정보
    private String receiverName;          // recptr
    private String receiverZip;           // recptrZipNo
    private String receiverAddress;       // recptrAdresNm
    private String receiverDetailAddress; // recptrDetailAdresNm
    private String receiverTel1;          // recptrTelno1
    private String receiverTel2;          // recptrTelno2
    private String receiverTel3;          // recptrTelno3
    private String receiverMobile1;       // recptrMbtlnum1
    private String receiverMobile2;       // recptrMbtlnum2
    private String receiverMobile3;       // recptrMbtlnum3

    // 4) 택배(물품) 기본 정보
    private String weightCode;            // wtUn (01~05)
    private String volumeCode;            // vlUn (01~05)
    private String content;               // cnNm
    private String contentCode;           // cnCd (001~029)

    // 5) 기타 부가정보
    private String specialRequest;        // dlvyPartclrMatter
    private String email;                 // email
    private String postOffice;            // visitRceptPstofc
    private String chrgeBndAt;            // chrgeBndAt (10:선불,16:착불)
    private String rceptResultRecptnAt;   // rceptResultRecptnAt (1:수신,3:거부)

    // 6) 접수 희망일/시간
    private String hopeRceptDe;           // hopeRceptDe (yyyyMMdd)
    private String visitRceptDe;          // visitRceptDe (08:00~12:00 등)

    // 7) 라벨 정보 (옵션)
    private boolean label;                // lblAt (true → "Y", false → "N")
    private String labelName;             // lblNm
    private String labelZip;              // lblZipNo
    private String labelAddress;          // lblAdresNm
    private String labelDetailAddress;    // lblDetailAdresNm
    private String labelTel1;             // lblTelno1
    private String labelTel2;             // lblTelno2
    private String labelTel3;             // lblTelno3
    private String labelMobile1;          // lblMbtlnum1
    private String labelMobile2;          // lblMbtlnum2
    private String labelMobile3;          // lblMbtlnum3

    // 8) 시스템/관리자용 필드 (옵션)
    private String userId;                // userId
    private String timeInterval;          // wishReceiptTimeInterval
    private String pickMan;               // pickMan
    private String pickArea;              // pickArea
    private String pickParty;             // pickParty
}
