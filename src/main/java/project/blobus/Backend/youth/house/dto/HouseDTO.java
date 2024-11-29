package project.blobus.Backend.youth.house.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDTO {
    private Long policyId;          // 정책 id(PK)

    private String polyRlmCd;       // 정책분야코드(일자리 : 023010 / 주거 : 023020)
    private String bizId;           // 정책번호
    private String polyBizSjnm;     // 정책명
    private String polyItcnCn;      // 정책소개(부제목)
    private String polyBizTy;       // 기관및지자체 구분
    private String mngtMson;        // 주관부처
    private String cnsgNmor;        // 운영기관
    private String sporCn;          // 지원내용
    private String bizPrdCn;        // 사업운영기간
    private String prdRpttSecd;     // 사업신청기간반복구분코드(상시:002001/연간반복:002002/월간반복:002003/특정기간:002004/미정:002005)
    private String rqutPrdCn;       // 사업신청기간
    private String sporScvl;        // 지원규모
    private String ageInfo;         // 연력
    private String prcpCn;          // 거주지 및 소득
    private String majrRqisCn;      // 전공요건
    private String empmSttsCn;      // 취업상태
    private String splzRlmRqisCn;   // 특화분야
    private String accrRqisCn;      // 학력요건
    private String aditRscn;        // 추가 단서사항
    private String prcpLmttTrgtCn;  // 참여제한대상
    private String rqutProcCn;      // 신청절차
    private String jdgnPresCn;      // 심사 및 발표
    private String rqutUrla;        // 신청사이트 주소
    private String rfcSiteUrla1;    // 참고사이트 url1
    private String rfcSiteUrla2;    // 참고사이트 url2
    private String pstnPaprCn;      // 제출서류
    private String etct;            // 기타
}
