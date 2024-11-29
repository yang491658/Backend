package project.blobus.Backend.member.role.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import project.blobus.Backend.member.role.common.entity.MemberRole;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AdminMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userPw;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
}
