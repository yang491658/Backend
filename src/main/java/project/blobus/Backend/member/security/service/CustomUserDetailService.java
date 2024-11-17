package project.blobus.Backend.member.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.blobus.Backend.member.basic.util.ModelMapper;
import project.blobus.Backend.member.member.business.entity.BusinessMember;
import project.blobus.Backend.member.member.business.repository.BusinessRepository;
import project.blobus.Backend.member.member.general.entity.GeneralMember;
import project.blobus.Backend.member.member.general.repository.GeneralRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final GeneralRepository generalRepository;
    private final BusinessRepository businessRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load User By Username");

        GeneralMember generalMember = generalRepository.findByUserId(username)
                .orElse(null);

        if (generalMember == null) {
            BusinessMember businessMember = businessRepository.findByUserId(username)
                    .orElseThrow(() -> new UsernameNotFoundException("NOT_FOUND"));

            log.info("Business Member Login");
            return ModelMapper.businessToMember(businessMember);
        }

        log.info("General Member Login");
        return ModelMapper.generalToMember(generalMember);
    }
}