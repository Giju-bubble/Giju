package com.bubble.giju.domain.delivery.service.impl;

import com.bubble.giju.domain.delivery.dto.DeliveryCreateRequestDto;
import com.bubble.giju.domain.delivery.jaxb.DeliveryRequestDto;
import com.bubble.giju.domain.delivery.jaxb.DeliveryResponseDto;
import com.bubble.giju.domain.delivery.service.DeliveryService;
import com.bubble.giju.global.config.DeliverySoapClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    @Value("${post.service-key}")
    private String serviceKey;

    private final DeliverySoapClient deliverySoapClient;

    @Override
    public DeliveryResponseDto addDelivery(DeliveryCreateRequestDto dto) {
        // 1) DeliveryCreateRequestDto → DeliveryRequestDto 변환
        DeliveryRequestDto request = buildDeliveryRequest(dto);

        // 2) serviceKey 세팅
        request.setServiceKey(serviceKey);
        log.info("▶ addDelivery 시작: reqNo={}, sender={}, receiver={}",
                request.getReqstNo(), dto.getSenderName(), dto.getReceiverName());
        // 3) SOAP 호출 (addDelivery)
        DeliveryResponseDto response = deliverySoapClient.addDelivery(request);

        // 4) 필요하다면, 응답 로그
        log.info("◀ addDelivery 완료:  agreeYn={}",  response.getAgreeYn());

        // 5) 최종 리턴
        return response;
    }

    /** 사용자 입력 DTO → JAXB 요청 DTO로 매핑 */
    private DeliveryRequestDto buildDeliveryRequest(DeliveryCreateRequestDto dto) {
        DeliveryRequestDto req = new DeliveryRequestDto();

        // 신청번호 (중복 방지용)
        req.setReqstNo("REQ" + System.currentTimeMillis());

        // 필수 필드
        req.setAgreeYn("Y");
        req.setHopeRceptDe(dto.getHopeRceptDe());
        req.setVisitRceptDe(dto.getVisitRceptDe());
        req.setChrgeBndAt(dto.getChrgeBndAt());
        req.setRceptResultRecptnAt(dto.getRceptResultRecptnAt());

        // 신청자 정보
        req.setApplcntNm(dto.getSenderName());
        req.setApplcntZipNo(dto.getSenderZip());
        req.setApplcntAdresNm(dto.getSenderAddress());
        req.setApplcntDetailAdresNm(dto.getSenderDetailAddress());
        req.setApplcntTelno1(dto.getSenderTel1());
        req.setApplcntTelno2(dto.getSenderTel2());
        req.setApplcntTelno3(dto.getSenderTel3());
        req.setApplcntMbtlnum1(dto.getSenderMobile1());
        req.setApplcntMbtlnum2(dto.getSenderMobile2());
        req.setApplcntMbtlnum3(dto.getSenderMobile3());

        // 수령자 정보
        req.setRecptr(dto.getReceiverName());
        req.setRecptrZipNo(dto.getReceiverZip());
        req.setRecptrAdresNm(dto.getReceiverAddress());
        req.setRecptrDetailAdresNm(dto.getReceiverDetailAddress());
        req.setRecptrTelno1(dto.getReceiverTel1());
        req.setRecptrTelno2(dto.getReceiverTel2());
        req.setRecptrTelno3(dto.getReceiverTel3());
        req.setRecptrMbtlnum1(dto.getReceiverMobile1());
        req.setRecptrMbtlnum2(dto.getReceiverMobile2());
        req.setRecptrMbtlnum3(dto.getReceiverMobile3());

        // 물품 정보
        req.setWtUn(dto.getWeightCode());
        req.setVlUn(dto.getVolumeCode());
        req.setCnNm(dto.getContent());
        req.setCnCd(dto.getContentCode());

        // 추가 옵션 필드
        req.setDlvyPartclrMatter(dto.getSpecialRequest());
        req.setEmail(dto.getEmail());
        req.setVisitRceptPstofc(dto.getPostOffice());

        // 라벨 정보
        req.setLblAt(dto.isLabel() ? "Y" : "N");
        req.setLblNm(dto.getLabelName());
        req.setLblZipNo(dto.getLabelZip());
        req.setLblAdresNm(dto.getLabelAddress());
        req.setLblDetailAdresNm(dto.getLabelDetailAddress());
        req.setLblTelno1(dto.getLabelTel1());
        req.setLblTelno2(dto.getLabelTel2());
        req.setLblTelno3(dto.getLabelTel3());
        req.setLblMbtlnum1(dto.getLabelMobile1());
        req.setLblMbtlnum2(dto.getLabelMobile2());
        req.setLblMbtlnum3(dto.getLabelMobile3());

        // 시스템/관리자용 필드
        req.setUserId(dto.getUserId());
        req.setWishReceiptTimeInterval(dto.getTimeInterval());
        req.setPickMan(dto.getPickMan());
        req.setPickArea(dto.getPickArea());
        req.setPickParty(dto.getPickParty());

        return req;
    }
}

