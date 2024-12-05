package project.blobus.Backend.youth.welfare;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class WelfareDTO {
    private int policyId; // 교육 정책 테이블 고유 ID값
    private String bizId; // 정책에 대한 ID 값
    private String policyName; // 정책명
    private String policyOverview; // 정책 소개
    private String policyContent1; // 정책 상세 내용 (지원 내용)
    private String supportScale; // 지원 규모
    private String policyOperatePeriod; // 사업 운영 기간
    private int policyDateType; // 사업신청기간반복구분코드
    private LocalDate policyApplicationStartPeriod;// 사업 신청 시작 기간
    private LocalDate policyApplicationEndPeriod;// 사업 신청 끝나는 기간
    private String policyApplicationPeriod; // 사업 신청 기간
    private String ageRequirement; // 연령 요건
    private String proposerRequirement; // 거주지 및 소득
    private String academicBackground; // 학력
    private String majorIn; // 전공
    private String employmentStatus; // 취업 상태
    private String additionalRequirement; // 추가 필요 사항
    private String applicationProcedure; // 신청 절차 내용
    private String judgingPresentation; // 심사 및 발표
    private String applicationSite; // 신청 사이트
    private String submitionDocument; // 제출 서류 내용
    private String hostOrganization; // 주관 기관
    private String hstOrgManagerName; // 주관 기관 담당자 이름
    private String hstOrgManagerPhone; // 주관 기관 담당자 연락처
    private String operatingAgency; // 운영 기관
    private String operAgenManagerName; // 운영 기관 담당자 이름
    private String operAgenManagerPhone; // 운영 기관 담당자 연락처
    private String referenceSite1; // 참고사이트1
    private String referenceSite2; // 참고사이트2
    private String etc; // 기타사항

    // 필요에 따라 Entity -> DTO 변환 메서드
    public WelfareDTO(WelfareEntity entity) {
        this.policyId = entity.getPolicyId();
        this.bizId = entity.getBizId();
        this.policyName = entity.getPolicyName();
        this.policyOverview = entity.getPolicyOverview();
        this.policyContent1 = entity.getPolicyContent1();
        this.supportScale = entity.getSupportScale();
        this.policyOperatePeriod = entity.getPolicyOperatePeriod();
        this.policyDateType = entity.getPolicyDateType();
        this.policyApplicationStartPeriod = entity.getPolicyApplicationStartPeriod();
        this.policyApplicationEndPeriod = entity.getPolicyApplicationEndPeriod();
        this.policyApplicationPeriod = entity.getPolicyApplicationPeriod();
        this.ageRequirement = entity.getAgeRequirement();
        this.proposerRequirement = entity.getProposerRequirement();
        this.academicBackground = entity.getAcademicBackground();
        this.majorIn = entity.getMajorIn();
        this.employmentStatus = entity.getEmploymentStatus();
        this.additionalRequirement = entity.getAdditionalRequirement();
        this.applicationProcedure = entity.getApplicationProcedure();
        this.judgingPresentation = entity.getJudgingPresentation();
        this.applicationSite = entity.getApplicationSite();
        this.submitionDocument = entity.getSubmitionDocument();
        this.hostOrganization = entity.getHostOrganization();
        this.hstOrgManagerName = entity.getHstOrgManagerName();
        this.hstOrgManagerPhone = entity.getHstOrgManagerPhone();
        this.operatingAgency = entity.getOperatingAgency();
        this.operAgenManagerName = entity.getOperAgenManagerName();
        this.operAgenManagerPhone = entity.getOperAgenManagerPhone();
        this.referenceSite1 = entity.getReferenceSite1();
        this.referenceSite2 = entity.getReferenceSite2();
        this.etc = entity.getEtc();
    }

    // DTO -> Entity 변환 메서드
    public WelfareEntity toEntity() {
        WelfareEntity entity = new WelfareEntity();
        entity.setPolicyId(this.policyId);
        entity.setBizId(this.bizId);
        entity.setPolicyName(this.policyName);
        entity.setPolicyOverview(this.policyOverview);
        entity.setPolicyContent1(this.policyContent1);
        entity.setSupportScale(this.supportScale);
        entity.setPolicyOperatePeriod(this.policyOperatePeriod);
        entity.setPolicyDateType(this.policyDateType);
        entity.setPolicyApplicationStartPeriod(this.policyApplicationStartPeriod);
        entity.setPolicyApplicationEndPeriod(this.policyApplicationEndPeriod);
        entity.setPolicyApplicationPeriod(this.policyApplicationPeriod);
        entity.setAgeRequirement(this.ageRequirement);
        entity.setProposerRequirement(this.proposerRequirement);
        entity.setAcademicBackground(this.academicBackground);
        entity.setMajorIn(this.majorIn);
        entity.setEmploymentStatus(this.employmentStatus);
        entity.setAdditionalRequirement(this.additionalRequirement);
        entity.setApplicationProcedure(this.applicationProcedure);
        entity.setJudgingPresentation(this.judgingPresentation);
        entity.setApplicationSite(this.applicationSite);
        entity.setSubmitionDocument(this.submitionDocument);
        entity.setHostOrganization(this.hostOrganization);
        entity.setHstOrgManagerName(this.hstOrgManagerName);
        entity.setHstOrgManagerPhone(this.hstOrgManagerPhone);
        entity.setOperatingAgency(this.operatingAgency);
        entity.setOperAgenManagerName(this.operAgenManagerName);
        entity.setOperAgenManagerPhone(this.operAgenManagerPhone);
        entity.setReferenceSite1(this.referenceSite1);
        entity.setReferenceSite2(this.referenceSite2);
        entity.setEtc(this.etc);
        return entity;
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
