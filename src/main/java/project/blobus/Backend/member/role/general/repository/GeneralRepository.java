package project.blobus.Backend.member.role.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.member.role.general.entity.GeneralMember;

import java.util.Optional;

public interface GeneralRepository extends JpaRepository<GeneralMember, Long> {
    boolean existsByPhoneNum(String phoneNum);

    Optional<GeneralMember> findByUserId(String username);

    Optional<GeneralMember> findByNameAndPhoneNum(String name, String phoneNum);
}
