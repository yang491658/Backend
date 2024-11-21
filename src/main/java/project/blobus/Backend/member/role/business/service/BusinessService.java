package project.blobus.Backend.member.role.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.basic.util.ModelMapper;
import project.blobus.Backend.member.role.business.dto.BusinessDTO;
import project.blobus.Backend.member.role.business.entity.BusinessMember;
import project.blobus.Backend.member.role.business.repository.BusinessRepository;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;

    // 기업계정 회원가입
    public Long register(BusinessDTO dto) {
        log.info("Business Register");

        BusinessMember member = ModelMapper.businessDtoToEntity(dto);
        member.setUserPw(passwordEncoder.encode(dto.getUserPw()));
        member.setJoinDate(LocalDate.now());
        businessRepository.save(member);

        return member.getId();
    }

    // 기업계정 회원가입 - 중복 확인
    public boolean duplicate(String userId) {
        log.info("Business Duplicate");

        return businessRepository.findByUserId(userId).isPresent();
    }
}
