package project.blobus.Backend.youth.education;//package project.blobus.Backend.youth.finance;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "youth_financial_policy")
//public class financeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer policyId; // 정책 게시물의 고유 ID
//
//    @Column(nullable = false, length = 255)
//    private String title; // 정책명: 지원 정책의 이름
//
//    @Column(columnDefinition = "TEXT")
//    private String overview; // 정책 개요: 해당 정책의 간략 설명과 주요 목적
//
//    @Column(length = 100)
//    private String benefitAmount; // 지원 금액 또는 혜택의 상세 내용
//
//    @Column(length = 50)
//    private String benefitType; // 지원 형태 (대출, 보조금, 감면 등)
//
//    @Column(length = 50)
//    private String ageRequirement; // 연령 조건 (예: 만 19세 이상 34세 이하)
//
//    @Column(length = 100)
//    private String incomeRequirement; // 소득 기준 (예: 중위소득 50% 이하)
//
//    @Column(length = 100)
//    private String residenceRequirement; // 거주 요건 (예: 부산시 거주)
//
//    @Temporal(TemporalType.DATE)
//    private Date applicationPeriodStart; // 신청 접수 시작일
//
//    @Temporal(TemporalType.DATE)
//    private Date applicationPeriodEnd; // 신청 접수 마감일
//
//    @Column(columnDefinition = "TEXT")
//    private String applicationMethod; // 신청 방법 (온라인 신청 링크 또는 오프라인 접수 방법 안내)
//
//    @Column(columnDefinition = "TEXT")
//    private String requiredDocuments; // 필요한 서류 및 제출 방법
//
//    @Column(columnDefinition = "TEXT")
//    private String selectionProcess; // 심사 및 선정 절차 (신청 후 심사 과정 설명)
//
//    @Column(length = 255)
//    private String resultAnnouncement; // 결과 발표 시기 및 방법
//
//    @Column(length = 100)
//    private String supportDuration; // 지원 혜택이 지속되는 기간
//
//    @Column(length = 255)
//    private String contactInfo; // 문의처 정보 (관련 부서나 담당자 기본 연락처)
//
//    @Column(length = 100)
//    private String contactEmail; // 문의처 이메일 주소
//
//    @Column(length = 15)
//    private String contactPhone; // 문의처 전화번호
//
//    @Column(columnDefinition = "TEXT")
//    private String referenceMaterials; // 참고 자료 및 링크 (추가 정보가 포함된 PDF나 외부 링크)
//
//    @Column(updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now(); // 게시글 작성일
//
//    private LocalDateTime updatedAt; // 마지막 수정일
//}
