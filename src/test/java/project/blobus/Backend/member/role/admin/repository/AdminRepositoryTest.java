package project.blobus.Backend.member.role.admin.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.blobus.Backend.member.role.admin.entity.AdminMember;
import project.blobus.Backend.member.role.common.entity.MemberRole;

@SpringBootTest
class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("관리자 회원 임시 등록")
    public void test() {
        AdminMember adminMember = AdminMember.builder()
                .userId("ADMIN")
                .userPw(passwordEncoder.encode("ADMIN"))
                .memberRole(MemberRole.ADMIN)
                .build();
        adminRepository.save(adminMember);
    }
}