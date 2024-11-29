package project.blobus.Backend.youth.education.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EducationDataApiDTO {

    @JsonProperty("bizId") // 정책에 대한 ID 값
    private String bizId;

    @JsonProperty("polyBizSjnm") // 정책명
    private String policyName;

    @JsonProperty("polyItcnCn") // 정책 소개
    private String policyOverview;

    @JsonProperty("sporCn") // 정책 상세 내용 (지원 내용)
    private String policyContent1;

    @JsonProperty("sporScvl") // 지원 규모
    private String supportScale;

    @JsonProperty("bizPrdCn") // 사업 운영 기간
    private String policyOperatePeriod;

    @JsonProperty("prdRpttSecd") // 사업신청기간반복구분코드
    private String policyDateType;

    @JsonProperty("rqutPrdCn") // 사업 신청 기간
    private String policyApplicationPeriod;

    @JsonProperty("ageInfo") // 연령 요건
    private String ageRequirement;

    @JsonProperty("prcpCn") // 거주지 및 소득
    private String proposerRequirement;

    @JsonProperty("accrRqisCn") // 학력
    private String academicBackground;

    @JsonProperty("majrRqisCn") // 전공
    private String majorIn;

    @JsonProperty("empmSttsCn") // 취업 상태
    private String employmentStatus;

    @JsonProperty("aditRscn") // 추가 필요 사항
    private String additionalRequirement;

    @JsonProperty("rqutProcCn") // 신청 절차 내용
    private String applicationProcedure;

    @JsonProperty("jdgnPresCn") // 심사 및 발표
    private String judgingPresentation;

    @JsonProperty("rqutUrla") // 신청 사이트
    private String applicationSite;

    @JsonProperty("pstnPaprCn") // 제출 서류 내용
    private String submitionDocument;

    @JsonProperty("mngtMson") // 주관 기관
    private String hostOrganization;

    @JsonProperty("mngtMrofCherCn") // 주관 기관 담당자 이름
    private String hstOrgManagerName;

    @JsonProperty("cherCtpcCn") // 주관 기관 담당자 연락처
    private String hstOrgManagerPhone;

    @JsonProperty("cnsgNmor") // 운영 기관
    private String operatingAgency;

    @JsonProperty("tintCherCn") // 운영 기관 담당자 이름
    private String operAgenManagerName;

    @JsonProperty("tintCherCtpcCn") // 운영 기관 담당자 연락처
    private String operAgenManagerPhone;

    @JsonProperty("rfcSiteUrla1") // 참고사이트1
    private String referenceSite1;

    @JsonProperty("rfcSiteUrla2") // 참고사이트2
    private String referenceSite2;

    @JsonProperty("etct") // 기타사항
    private String etc;

}
