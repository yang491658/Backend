package project.blobus.Backend.member.role.general.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.member.basic.util.ModelMapper;
import project.blobus.Backend.member.role.general.dto.GeneralDTO;
import project.blobus.Backend.member.role.general.entity.GeneralMember;
import project.blobus.Backend.member.role.general.repository.GeneralRepository;

import java.time.LocalDate;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GeneralService {
    private final GeneralRepository generalRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    // 일반계정 회원가입
    public Long register(GeneralDTO generalDTO) {
        log.info("GeneralMember Register");

        if (generalRepository.existsByPhoneNum(generalDTO.getPhoneNum()))
            return 0L;

        GeneralMember generalMember = ModelMapper.generalDtoToEntity(generalDTO);
        generalMember.setUserPw(passwordEncoder.encode(generalDTO.getUserPw()));
        generalMember.setJoinDate(LocalDate.now());
        generalRepository.save(generalMember);

        return generalMember.getId();
    }

    // 일반계정 회원가입 - 중복 확인
    public boolean duplicate(String userId) {
        log.info("GeneralMember Duplicate");

        return generalRepository.findByUserId(userId).isPresent();
    }

    // 일반계정 회원가입 - 메일 전송
    public Long sendEmail(String to) {
        log.info(("GeneralMember Send Email"));

        // TODO 메일 제목 수정
        String subject = "이메일 인증";
        // TODO 메일 내용 JS로 변경
        Long code = generateCode();
        String text = "인증코드 : " + String.format("%06d", code);

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(to);
        emailMessage.setSubject(subject);
        emailMessage.setText(text);

        mailSender.send(emailMessage);

        return code;
    }

    private Long generateCode() {
        Random random = new Random();
        Long code = (long) random.nextInt(999999);
        return code;
    }

    // 일반계정 아이디 찾기
    public String find(String name, String phoneNum) {
        log.info("GeneralMember Find ID");

        GeneralMember generalMember = generalRepository.findByNameAndPhoneNum(name, phoneNum)
                .orElseThrow(() -> new UsernameNotFoundException("NOT_FOUND"));


        return generalMember.getUserId();
    }
}
