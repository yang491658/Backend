package project.blobus.Backend.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommuntiyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String authorId;
    private String authorName;
    private boolean visibility;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private CommunityPost post;
}
