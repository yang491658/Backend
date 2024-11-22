package project.blobus.Backend.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.blobus.Backend.community.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    // private Long User; 사용자 코드 커밋할 때 다시 작성

    private Long id;

    private String title;

    private String content;

    private Long authorId; // 작성자 ID
    private String authorName; // 작성자 이름 (추가)

    private Post.BoardType boardType; // 게시글 종류 (자유/건의)
    private Post.UserType userType; // 사용자 타입 (청년/기업)

    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 수정 시간

    private List<CommentDTO> comments; // 댓글 정보

}
