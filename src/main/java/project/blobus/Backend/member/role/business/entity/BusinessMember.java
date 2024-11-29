package project.blobus.Backend.member.role.business.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.blobus.Backend.member.role.common.entity.MemberRole;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userPw;

    private String name;
    private String phoneNum;
    private String email;
    private String address;

    private boolean delFlag;
    private int loginErrorCount;
    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
}