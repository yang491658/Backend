package project.blobus.Backend.member.member.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO implements UserDetails {
    private Long id;
    private String userId;
    private String userPw;

    private String name;
    private String address;
    private String phoneNum;
    private String email;

    private LocalDate birthDate;
    private String gender;
    private boolean foreigner;

    private String roleName;
    private Collection<? extends GrantedAuthority> authorities;

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("id", id);
        dataMap.put("userId", userId);
        dataMap.put("name", name);
        dataMap.put("address", address);
        dataMap.put("phoneNum", phoneNum);
        dataMap.put("email", email);
        dataMap.put("birthDate", birthDate != null ? birthDate.toString() : null);
        dataMap.put("gender", gender);
        dataMap.put("foreigner", foreigner);
        dataMap.put("roleName", roleName);

        return dataMap;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
