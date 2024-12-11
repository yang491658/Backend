package project.blobus.Backend.youth.education;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EducationDTO {
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
    private String participationRestriction; // 참여 제한 대상
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
    private boolean delFlag; // 삭제 로그 flag

    @Autowired
    private ModelMapper modelMapper;

    // Entity -> DTO 변환 메서드
    public EducationDTO fromEntity(EducationEntity entity) {
        return modelMapper.map(entity, EducationDTO.class);
    }

    // DTO -> Entity 변환 메서드
    public EducationEntity toEntity() {
        return modelMapper.map(this, EducationEntity.class);
    }
}