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
public class BookmarkDTO {
    private Long id;
    private String mainCategory;
    private String subCategory;
    private Long targetId;
    private String title;
    private String content;

    private LocalDate startDate;
    private LocalDate endDate;
    private String place;

    private LocalDateTime atTime;
    private String link;
}
