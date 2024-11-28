package project.blobus.Backend.member.role.common.dto;

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
    private String phoneNum;
    private String email;
    private String address;

    private LocalDate birthDate;
    private String gender;
    private Boolean foreigner;

    private String customSetting;

    private boolean delFlag;
    private int loginErrorCount;
    private LocalDate joinDate;

    private String roleName;
    private Collection<? extends GrantedAuthority> authorities;

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("id", id);
        dataMap.put("userId", userId);

        dataMap.put("name", name);
        dataMap.put("phoneNum", phoneNum);
        dataMap.put("email", email);
        dataMap.put("address", address);

        dataMap.put("birthDate", birthDate != null ? birthDate.toString() : null);
        dataMap.put("gender", gender);
        dataMap.put("foreigner", foreigner);

        dataMap.put("customSetting", customSetting);

        dataMap.put("joinDate", joinDate != null ? joinDate.toString() : null);
        dataMap.put("loginErrorCount", loginErrorCount);
        dataMap.put("delFlag", delFlag);

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
