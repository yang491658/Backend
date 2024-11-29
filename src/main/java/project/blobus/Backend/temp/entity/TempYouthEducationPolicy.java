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
public class TempYouthEducationPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;
    private String programName;
    private String overview;
    private String supportAmount;
    private String supportType;
    private String ageRequirement;
    private String academicRequirement;
    private LocalDate applicationPeriodStart;
    private LocalDate applicationPeriodEnd;
    private String applicationMethod;
    private String requiredDocuments;
    private String curriculum;
    private String schedule;
    private String location;
    private String duration;
    private String selectionCriteria;
    private String postProgramBenefits;
    private String contactInfo;
    private String contactEmail;
    private String contactPhone;
    private String referenceMaterials;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
