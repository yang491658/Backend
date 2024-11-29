package project.blobus.Backend.member.role.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.member.role.admin.entity.AdminMember;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminMember, Long> {
    Optional<AdminMember> findByUserId(String username);
}
