package project.blobus.Backend.member.role.admin.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.role.admin.entity.AdminMember;
import project.blobus.Backend.member.role.admin.repository.AdminRepository;
import project.blobus.Backend.member.role.common.entity.MemberRole;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        register();
    }

    public void register() {
        AdminMember adminMember = AdminMember.builder()
                .userId("ADMIN")
                .userPw(passwordEncoder.encode("ADMIN"))
                .memberRole(MemberRole.ADMIN)
                .build();

        if (!repository.existsById(1L))
            repository.save(adminMember);
    }
}
