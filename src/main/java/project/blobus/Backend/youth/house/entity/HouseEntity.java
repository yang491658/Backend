package project.blobus.Backend.youth.house.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name= "tbl_youth_housing_policy")
@Builder
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;          // 정책 id(PK)

    private String polyRlmCd;       // 정책분야코드(일자리 : 023010 / 주거 : 023020)

    private String bizId;           // 정책번호

    private String polyBizSjnm;     // 정책명

    private String polyItcnCn;      // 정책소개(부제목)

    private String polyBizTy;       // 기관및지자체 구분

    private String mngtMson;        // 주관기관

    private String cherCtpcCn;      // 주관기관 담당자 연락처

    private String cnsgNmor;        // 운영기관

    private String tintCherCtpcCn;  // 운영기관 담당자 연락처

    @Column(columnDefinition = "TEXT")
    private String sporCn;          // 지원내용

    private String bizPrdCn;        // 사업운영기간

    private String prdRpttSecd;     // 사업신청기간반복구분코드(상시:002001/연간반복:002002/월간반복:002003/특정기간:002004/미정:002005)

    @Column(columnDefinition = "TEXT")
    private String rqutPrdCn;       // 사업신청기간

    private LocalDate rqutPrdStart; // 사업신청 시작일

    private LocalDate rqutPrdEnd;   // 사업신청 종료일

    private String rqutPrdCnSts;    // 신청기간 상태(진행중/마감)

    private String sporScvl;        // 지원규모

    private String ageInfo;         // 연령

    @Column(columnDefinition = "TEXT")
    private String prcpCn;          // 거주지 및 소득

    private String majrRqisCn;      // 전공요건

    @Column(columnDefinition = "TEXT")
    private String empmSttsCn;      // 취업상태

    private String splzRlmRqisCn;   // 특화분야

    private String accrRqisCn;      // 학력요건

    @Column(columnDefinition = "TEXT")
    private String aditRscn;        // 추가세부사항

    @Column(columnDefinition = "TEXT")
    private String prcpLmttTrgtCn;  // 참여제한대상

    @Column(columnDefinition = "TEXT")
    private String rqutProcCn;      // 신청절차

    @Column(columnDefinition = "TEXT")
    private String jdgnPresCn;      // 신청 및 발표

    @Column(columnDefinition = "TEXT")
    private String rqutUrla;        // 신청사이트 주소

    private String rfcSiteUrla1;    // 참고사이트 url1

    private String rfcSiteUrla2;    // 참고사이트 url2

    @Column(columnDefinition = "TEXT")
    private String pstnPaprCn;      // 제출서류

    @Column(columnDefinition = "TEXT")
    private String etct;            // 기타

    private boolean delFlag;        // 삭제상태(true:삭제됨/false:삭제안됨)

    public void setPolyRlmCd(String polyRlmCd) {
        this.polyRlmCd = polyRlmCd;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public void setPolyBizSjnm(String polyBizSjnm) {
        this.polyBizSjnm = polyBizSjnm;
    }

    public void setPolyItcnCn(String polyItcnCn) {
        this.polyItcnCn = polyItcnCn;
    }

    public void setPolyBizTy(String polyBizTy) {
        this.polyBizTy = polyBizTy;
    }

    public void setMngtMson(String mngtMson) {
        this.mngtMson = mngtMson;
    }

    public void setCherCtpcCn(String cherCtpcCn) {
        this.cherCtpcCn = cherCtpcCn;
    }

    public void setCnsgNmor(String cnsgNmor) {
        this.cnsgNmor = cnsgNmor;
    }

    public void setTintCherCtpcCn(String tintCherCtpcCn) {
        this.tintCherCtpcCn = tintCherCtpcCn;
    }

    public void setSporCn(String sporCn) {
        this.sporCn = sporCn;
    }

    public void setBizPrdCn(String bizPrdCn) {
        this.bizPrdCn = bizPrdCn;
    }

    public void setPrdRpttSecd(String prdRpttSecd) {
        this.prdRpttSecd = prdRpttSecd;
    }

    public void setRqutPrdCn(String rqutPrdCn) {
        this.rqutPrdCn = rqutPrdCn;
    }

    public void setRqutPrdStart(LocalDate rqutPrdStart) {
        this.rqutPrdStart = rqutPrdStart;
    }

    public void setRqutPrdEnd(LocalDate rqutPrdEnd) {
        this.rqutPrdEnd = rqutPrdEnd;
    }

    public void setRqutPrdCnSts(String rqutPrdCnSts) {
        this.rqutPrdCnSts = rqutPrdCnSts;
    }

    public void setSporScvl(String sporScvl) {
        this.sporScvl = sporScvl;
    }

    public void setAgeInfo(String ageInfo) {
        this.ageInfo = ageInfo;
    }

    public void setPrcpCn(String prcpCn) {
        this.prcpCn = prcpCn;
    }

    public void setMajrRqisCn(String majrRqisCn) {
        this.majrRqisCn = majrRqisCn;
    }

    public void setEmpmSttsCn(String empmSttsCn) {
        this.empmSttsCn = empmSttsCn;
    }

    public void setSplzRlmRqisCn(String splzRlmRqisCn) {
        this.splzRlmRqisCn = splzRlmRqisCn;
    }

    public void setAccrRqisCn(String accrRqisCn) {
        this.accrRqisCn = accrRqisCn;
    }

    public void setAditRscn(String aditRscn) {
        this.aditRscn = aditRscn;
    }

    public void setPrcpLmttTrgtCn(String prcpLmttTrgtCn) {
        this.prcpLmttTrgtCn = prcpLmttTrgtCn;
    }

    public void setRqutProcCn(String rqutProcCn) {
        this.rqutProcCn = rqutProcCn;
    }

    public void setJdgnPresCn(String jdgnPresCn) {
        this.jdgnPresCn = jdgnPresCn;
    }

    public void setRqutUrla(String rqutUrla) {
        this.rqutUrla = rqutUrla;
    }

    public void setRfcSiteUrla1(String rfcSiteUrla1) {
        this.rfcSiteUrla1 = rfcSiteUrla1;
    }

    public void setRfcSiteUrla2(String rfcSiteUrla2) {
        this.rfcSiteUrla2 = rfcSiteUrla2;
    }

    public void setPstnPaprCn(String pstnPaprCn) {
        this.pstnPaprCn = pstnPaprCn;
    }

    public void setEtct(String etct) {
        this.etct = etct;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }
}
