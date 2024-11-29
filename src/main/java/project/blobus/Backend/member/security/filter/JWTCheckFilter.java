package project.blobus.Backend.member.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.blobus.Backend.member.role.common.dto.MemberDTO;
import project.blobus.Backend.member.security.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("Should Not Filter");

//        if (request.getMethod().equals("OPTIONS")) {
//            log.info("Is OPTIONS");
//            return true;
//        }
//        String path = request.getRequestURI();
//        if (path.startsWith("/member/")) {
//            log.info("Is Member");
//            return true;
//        }
//
//        return false;
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Do Filter Internal");

        String authHeaderStr = request.getHeader("Authorization");
        try {
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claim = JWTUtil.validateToken(accessToken);

            String userId = (String) claim.get("userId");

            String name = (String) claim.get("name");
            String phoneNum = (String) claim.get("phoneNum");
            String email = (String) claim.get("email");
            String address = (String) claim.get("address");

            LocalDate birthDate = LocalDate.parse((String) claim.get("birthDate"));
            String gender = (String) claim.get("gender");
            Boolean foreigner = (Boolean) claim.get("foreigner");

            String customSetting = (String) claim.get("customSetting");

            boolean delFlag = (boolean) claim.get("delFlag");
            int loginErrorCount = (int) claim.get("loginErrorCount");
            LocalDate joinDate = LocalDate.parse((String) claim.get("joinDate"));

            String roleName = (String) claim.get("roleName");

            MemberDTO memberDTO = MemberDTO.builder()
                    .userId(userId)
                    .name(name)
                    .phoneNum(phoneNum)
                    .email(email)
                    .address(address)
                    .birthDate(birthDate)
                    .gender(gender)
                    .foreigner(foreigner)
                    .customSetting(customSetting)
                    .delFlag(delFlag)
                    .loginErrorCount(loginErrorCount)
                    .joinDate(joinDate)
                    .roleName(roleName)
                    .build();

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + memberDTO.getRoleName()));

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberDTO, memberDTO.getPassword(), authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            Gson gson = new Gson();

            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }
}
