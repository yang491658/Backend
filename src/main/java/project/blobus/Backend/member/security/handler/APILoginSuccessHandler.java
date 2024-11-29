package project.blobus.Backend.member.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.blobus.Backend.member.role.admin.entity.AdminMember;
import project.blobus.Backend.member.role.admin.repository.AdminRepository;
import project.blobus.Backend.member.role.business.entity.BusinessMember;
import project.blobus.Backend.member.role.business.repository.BusinessRepository;
import project.blobus.Backend.member.role.common.dto.MemberDTO;
import project.blobus.Backend.member.role.general.entity.GeneralMember;
import project.blobus.Backend.member.role.general.repository.GeneralRepository;
import project.blobus.Backend.member.security.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;

@Log4j2
@Component
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {
    private final GeneralRepository generalRepository;
    private final BusinessRepository businessRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public APILoginSuccessHandler(GeneralRepository generalRepository, BusinessRepository businessRepository, AdminRepository adminRepository) {
        this.generalRepository = generalRepository;
        this.businessRepository = businessRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("API Login Success Handler");

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();
        Map<String, Object> claims = memberDTO.getClaims();

        int min = 24 * 60;
        claims.put("accessToken", JWTUtil.generateToken(claims, min));
        claims.put("expirationTime", LocalDateTime.now().plusMinutes(min).toString());

        String userId = request.getParameter("username");
        GeneralMember generalMember = generalRepository.findByUserId(userId)
                .orElse(null);

        if (generalMember != null) {
            generalMember.setLoginErrorCount(0);
            generalRepository.save(generalMember);
        } else {
            BusinessMember businessMember = businessRepository.findByUserId(userId)
                    .orElse(null);

            if (businessMember != null) {
                businessMember.setLoginErrorCount(0);
                businessRepository.save(businessMember);
            } else {
                AdminMember adminMember = adminRepository.findByUserId(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("NOT_FOUND"));

                adminRepository.save(adminMember);
            }
        }

        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
