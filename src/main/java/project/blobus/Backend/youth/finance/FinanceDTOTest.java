package project.blobus.Backend.youth.finance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class FinanceDTOTest {
    private Integer policyId;
    private String title;
    private String overview;
    private String applicationPeriodStart; // Date를 String으로 변환
    private String applicationPeriodEnd;
    private String contactInfo;
    private String contactPhone;

    // 필요에 따라 Entity -> DTO 변환 메서드
    public FinanceDTOTest(FinanceEntityTest entity) {
        this.policyId = entity.getPolicyId();
        this.title = entity.getTitle();
        this.overview = entity.getOverview();
        this.applicationPeriodStart = entity.getApplicationPeriodStart().toString();
        this.applicationPeriodEnd = entity.getApplicationPeriodEnd().toString();
        this.contactInfo = entity.getContactInfo();
        this.contactPhone = entity.getContactPhone();
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
