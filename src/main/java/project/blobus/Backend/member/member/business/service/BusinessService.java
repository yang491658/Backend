package project.blobus.Backend.member.member.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.basic.util.ModelMapper;
import project.blobus.Backend.member.member.business.dto.BusinessDTO;
import project.blobus.Backend.member.member.business.entity.BusinessMember;
import project.blobus.Backend.member.member.business.repository.BusinessRepository;

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
    public Long register(BusinessDTO businessDTO) {
        log.info("Business Register");

        BusinessMember businessMember = ModelMapper.businessDtoToEntity(businessDTO);
        businessMember.setUserPw(passwordEncoder.encode(businessDTO.getUserPw()));
        businessMember.setJoinDate(LocalDate.now());
        businessRepository.save(businessMember);

        return businessMember.getId();
    }

    // 기업계정 회원가입 - 중복 확인
    public boolean duplicate(String userId) {
        log.info("Business Duplicate");

        Optional<BusinessMember> optionalBusinessMember = businessRepository.findByUserId(userId);

        return optionalBusinessMember.isPresent();
    }
}
