package project.blobus.Backend.member.member.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.member.member.general.entity.GeneralMember;

import java.util.Optional;

public interface GeneralRepository extends JpaRepository<GeneralMember, Long> {
    Optional<GeneralMember> findByUserId(String username);
}
