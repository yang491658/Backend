package project.blobus.Backend.community;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "community_comment")
public class CommunityComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩으로 최적화
    @JoinColumn(name = "post_id", nullable = false) // post_id는 외래 키
    private CommunityEntity communityEntity; // 댓글이 속한 게시글

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommunityEntity.BoardType boardType; // 게시판 종류 (자유, 건의)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommunityEntity.UserType userType; // 유저 타입 (청년, 기업)

    @Column(nullable = false)
    // 댓글 작성자의 ID, null 값 허용 불가
    private Long authorId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @Column(nullable = false, columnDefinition = "TEXT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate // 수정 이벤트가 발생할 때 실행되는 메서드
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        // 수정 시 수정 일자(updatedAt)를 현재 시간으로 갱신
    }

}
