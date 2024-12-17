package project.blobus.Backend.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomDTO {
    private Long targetId;
    private String mainCategory;
    private String subCategory;
    private String title;
    private String content;

    private LocalDate startDate;
    private LocalDate endDate;
    private String place;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String link;
}
