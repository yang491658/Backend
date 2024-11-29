package project.blobus.Backend.community.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.blobus.Backend.community.entity.Post;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostListDTO {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String authorId;
//    private Post.UserType userType;
//    private Post.BoardType boardType;


    public PostListDTO(Long id, String title, LocalDateTime createdAt, String authorId) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.authorId = authorId;
//        this.userType = userType;
//        this.boardType = boardType;
    }
}
