package project.blobus.Backend.community.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("FREE") // post_type 컬럼에 저장될 값
public class CommunityFreeBoard extends Post {
    public CommunityFreeBoard(Long id, String title, String content, Long authorId, BoardType boardType, UserType userType, LocalDateTime createdAt, LocalDateTime updateAt, List<CommunityComment> comments) {
        super(id, title, content, authorId, boardType, userType, createdAt, updateAt, comments);  // 부모 클래스 생성자 호출
    }

}
