package project.blobus.Backend.temp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TempYouthFinancialPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;
    private String title;
    private String overview;
    private String benefitAmount;
    private String benefitType;
    private String ageRequirement;
    private String incomeRequirement;
    private String residenceRequirement;
    private LocalDate applicationPeriodStart;
    private LocalDate applicationPeriodEnd;
    private String applicationMethod;
    private String requiredDocuments;
    private String selectionProcess;
    private String resultAnnouncement;
    private String supportDuration;
    private String contactInfo;
    private String contactEmail;
    private String contactPhone;
    private String referenceMaterials;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
