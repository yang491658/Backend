package project.blobus.Backend.member.role.general.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralDTO {
    private Long id;
    private String userId;
    private String userPw;

    private String name;
    private String phoneNum;
    private String address;

    private LocalDate birthDate;
    private String gender;
    private Boolean foreigner;

    private String roleName;
    private Collection<? extends GrantedAuthority> authorities;
}
