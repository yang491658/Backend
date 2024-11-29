package project.blobus.Backend.youth.finance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "youth_financial_policy_test")
public class FinanceEntityTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer policyId; // 정책 게시물의 고유 ID

    @Column(nullable = false, length = 255)
    private String title; // 정책명: 지원 정책의 이름

    @Column(columnDefinition = "TEXT")
    private String overview; // 정책 개요: 해당 정책의 간략 설명과 주요 목적

    @Temporal(TemporalType.DATE)
    private Date applicationPeriodStart; // 신청 접수 시작일

    @Temporal(TemporalType.DATE)
    private Date applicationPeriodEnd; // 신청 접수 마감일

    @Column(length = 255)
    private String contactInfo; // 담당기관명

    @Column(length = 15)
    private String contactPhone; // 문의처 전화번호
}
