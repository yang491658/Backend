package project.blobus.Backend.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("SUGGESTION") // post_type 컬럼에 저장될 값
public class  CommunitySuggestBoard extends Post {

    @Enumerated(EnumType.STRING)
    // Enum 값을 문자열로 저장 (ex. PUBLIC, PRIVATE)
    @Column(nullable = false)
    // 게시글 공개 여부
    private Visibility visibility;

    CommunitySuggestBoard(Long id, String title, String content, Long authorId, BoardType boardType, UserType userType, LocalDateTime createdAt, LocalDateTime updatedAt, List<CommunityComment> comments) {
        super(id, title, content, authorId, boardType, userType, createdAt, updatedAt, comments);
    }

    // 게시글 공개 여부를 구분하는 ENUM 타입
    public enum  Visibility {
        PUBLIC, // 공개
        PRIVATE // 비공개
    }
}
