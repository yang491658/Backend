package project.blobus.Backend.youth.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobRecruitDTO {
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
}
