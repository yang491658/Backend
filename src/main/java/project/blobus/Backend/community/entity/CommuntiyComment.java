package project.blobus.Backend.community.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "post")
public class CommuntiyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorId;
    private String authorName;
    private String authorEmail;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private boolean visibility;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private CommunityPost post;
}
