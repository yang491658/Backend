package project.blobus.Backend.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentdDTO {
    private Long id;
    private String boadrType;
    private String category;
    private String title;
    private String content;
    private String authorId;
    private boolean visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
