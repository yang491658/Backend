package project.blobus.Backend.member.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import project.blobus.Backend.member.role.business.entity.BusinessMember;
import project.blobus.Backend.member.role.business.repository.BusinessRepository;
import project.blobus.Backend.member.role.general.entity.GeneralMember;
import project.blobus.Backend.member.role.general.repository.GeneralRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
@Component
public class APILoginFailHandler implements AuthenticationFailureHandler {
    private final GeneralRepository generalRepository;
    private final BusinessRepository businessRepository;

    @Autowired
    public APILoginFailHandler(GeneralRepository generalRepository, BusinessRepository businessRepository) {
        this.generalRepository = generalRepository;
        this.businessRepository = businessRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("API Login Fail Handler");

        String userId = request.getParameter("username");
        boolean isDel;
        boolean lock;
        try {
            GeneralMember generalMember = generalRepository.findByUserId(userId)
                    .orElse(null);

            if (generalMember != null) {
                isDel = generalMember.isDelFlag();
                lock = generalMember.getLoginErrorCount() >= 5;

                generalMember.setLoginErrorCount(generalMember.getLoginErrorCount() + 1);
                generalRepository.save(generalMember);
            } else {
                BusinessMember businessMember = businessRepository.findByUserId(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("NOT_FOUND"));

                isDel = businessMember.isDelFlag();
                lock = businessMember.getLoginErrorCount() >= 5;

                businessMember.setLoginErrorCount(businessMember.getLoginErrorCount() + 1);
                businessRepository.save(businessMember);
            }
        } catch (UsernameNotFoundException e) {
            log.error(userId + " Member Not Found : " + e);
            throw e;
        }

        Gson gson = new Gson();

        String jsonStr = gson.toJson(Map.of("error", isDel ? "DELETE" : lock ? "LOCK" : "ERROR_LOGIN"));

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
