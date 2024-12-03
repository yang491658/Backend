package project.blobus.Backend.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Long id;
    private String boardType;
    private String category;
    private String title;
    private String content;
    private String authorId;
    private String authorName;
    private String authorEmail;
    private boolean toEmail;
    private boolean visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDTO> commentList;
}
