package project.blobus.Backend.mypage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.blobus.Backend.member.role.general.entity.GeneralMember;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private GeneralMember member;
    private String mainCategory;
    private String subCategory;
    private Long targetId;
    private LocalDateTime atTime;
}
