package project.blobus.Backend.youth.job.entity;

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

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name= "tbl_youth_job_recruit")
@Builder
public class JobRecruitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobRecruitId;          // 구인구직 id(PK)

    private String wantedAuthNo;        // 구인인증번호
    private String company;             // 회사명
    private String title;               // 채용제목
    private String career;              // 경력
    private String minEdubg;            // 최소학력
    private String maxEdubg;            // 최대학력
    private String region;              // 근무지역
    private String regDt;               // 등록일자
    private String closeDt;             // 마감일자
    private String wantedInfoUrl;       // 워크넷 채용정보 URL
    private String wantedMobileInfoUrl; // 워크넷 모바일 채용정보 URL
    private int jobsCd;                 // 직종코드

    public void setWantedAuthNo(String wantedAuthNo) {
        this.wantedAuthNo = wantedAuthNo;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public void setMinEdubg(String minEdubg) {
        this.minEdubg = minEdubg;
    }

    public void setMaxEdubg(String maxEdubg) {
        this.maxEdubg = maxEdubg;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public void setCloseDt(String closeDt) {
        this.closeDt = closeDt;
    }

    public void setWantedInfoUrl(String wantedInfoUrl) {
        this.wantedInfoUrl = wantedInfoUrl;
    }

    public void setWantedMobileInfoUrl(String wantedMobileInfoUrl) {
        this.wantedMobileInfoUrl = wantedMobileInfoUrl;
    }

    public void setJobsCd(int jobsCd) {
        this.jobsCd = jobsCd;
    }
}
