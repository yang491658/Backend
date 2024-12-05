package project.blobus.Backend.community.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "commentList")
public class CommunityPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorId;
    private String authorName;
    private String authorEmail;

    private String boardType;
    private String category;
    private String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private boolean visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<CommuntiyComment> commentList;
}
