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
public class TempYouthStartupPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;
    private String programName;
    private String overview;
    private String supportType;
    private String supportAmount;
    private String ageRequirement;
    private String startupStatus;
    private String locationRequirement;
    private LocalDate applicationPeriodStart;
    private LocalDate applicationPeriodEnd;
    private String applicationMethod;
    private String requiredDocuments;
    private String supportDuration;
    private String mentoringContent;
    private String workspaceSupport;
    private String postProgramSupport;
    private String successSupport;
    private String contactInfo;
    private String contactEmail;
    private String contactPhone;
    private String referenceMaterials;
    private String businessPlanTips;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
