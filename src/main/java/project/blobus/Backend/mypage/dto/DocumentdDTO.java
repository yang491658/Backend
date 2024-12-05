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
    private String boardType;
    private String category;
    private String title;
    private String content;
    private String authorId;
    private String authorName;
    private String authorEmail;
    private boolean visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int commentCount;
}
