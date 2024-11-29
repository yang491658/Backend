package project.blobus.Backend.member.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.blobus.Backend.member.basic.util.ModelMapper;
import project.blobus.Backend.member.role.admin.entity.AdminMember;
import project.blobus.Backend.member.role.admin.repository.AdminRepository;
import project.blobus.Backend.member.role.business.entity.BusinessMember;
import project.blobus.Backend.member.role.business.repository.BusinessRepository;
import project.blobus.Backend.member.role.general.entity.GeneralMember;
import project.blobus.Backend.member.role.general.repository.GeneralRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final GeneralRepository generalRepository;
    private final BusinessRepository businessRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Load User By Username");

        GeneralMember generalMember = generalRepository.findByUserId(username)
                .orElse(null);

        if (generalMember == null) {
            BusinessMember businessMember = businessRepository.findByUserId(username)
                    .orElse(null);

            if (businessMember == null) {
                AdminMember adminMember = adminRepository.findByUserId(username)
                        .orElseThrow(() -> new UsernameNotFoundException("NOT_FOUND"));

                log.info("Admin Member Login");
                return ModelMapper.adminToMember(adminMember);
            }

            log.info("Business Member Login");
            return ModelMapper.businessToMember(businessMember);
        }

        log.info("General Member Login");
        return ModelMapper.generalToMember(generalMember);
    }
}