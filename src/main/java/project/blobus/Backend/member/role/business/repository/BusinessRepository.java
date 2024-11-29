package project.blobus.Backend.member.role.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.member.role.business.entity.BusinessMember;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<BusinessMember, Long> {
    Optional<BusinessMember> findByUserId(String username);
}
