package project.blobus.Backend.member.member.general.entity;

import jakarta.persistence.*;
import lombok.*;
import project.blobus.Backend.member.member.common.entity.MemberRole;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GeneralMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userPw;

    private String name;
    private String phoneNum;
    private String address;

    private LocalDate birthDate;
    private String gender;
    private boolean foreigner;

    private boolean delFlag;
    private int loginErrorCount;
    private LocalDate joinDate;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
}