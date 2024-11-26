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
    private String title;
    private String content;
    private String authorId;
    private String boardType;
    private String boardCategory;
    private String visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
