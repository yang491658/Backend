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
public class TempYouthJobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;
    private String companyName;
    private String jobTitle;
    private String jobDescription;
    private String jobType;
    private String location;
    private String salary;
    private String qualification;
    private LocalDate applicationDeadline;
    private String contactInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
}
