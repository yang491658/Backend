package project.blobus.Backend.resource.entity;

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
public class ResourceCulture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long resNum;
    private String title;

    private LocalDate startDate;
    private LocalDate endDate;

    private String place;
    private String address;

    private String imgUrl;
    private String link;

    private String category;
    private String subcategory;

    private LocalDateTime createdAt;
}
