package project.blobus.Backend.member.member.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessDTO {
    private Long id;
    private String userId;
    private String userPw;

    private String name;
    private String phoneNum;
    private String email;
    private String address;

    private String roleName;
    private Collection<? extends GrantedAuthority> authorities;
}