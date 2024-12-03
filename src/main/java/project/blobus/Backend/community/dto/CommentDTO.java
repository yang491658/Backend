package project.blobus.Backend.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String content;
    private String authorId;
    private String authorName;
    private boolean visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId;
}
