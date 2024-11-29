package project.blobus.Backend.member.role.general.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class GeneralService {
    private final GeneralRepository generalRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    // 일반계정 회원가입
    public Long register(GeneralDTO dto) {
        log.info("GeneralMember Register");

        if (generalRepository.existsByPhoneNum(dto.getPhoneNum()))
            return 0L;

        GeneralMember member = ModelMapper.generalDtoToEntity(dto);
        member.setUserPw(passwordEncoder.encode(dto.getUserPw()));
        member.setJoinDate(LocalDate.now());
        generalRepository.save(member);

        return member.getId();
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

        GeneralMember member = generalRepository.findByNameAndPhoneNum(name, phoneNum)
                .orElseThrow(() -> new UsernameNotFoundException("NOT_FOUND"));

        if (member.isDelFlag())
            return "DELETE";
        if (member.getLoginErrorCount() > 5)
            return "LOCK";


        return member.getUserId();
    }

    // 일반계정 비밀번호 찾기(변경) + 회원정보 수정
    public void modify(GeneralDTO dto) {
        log.info(("GeneralMember Send Email"));

        GeneralMember member = generalRepository.findByUserId(dto.getUserId()).orElseThrow();

        // 비밀번호 찾기(변경)
        if (dto.getUserPw() != null) member.setUserPw(passwordEncoder.encode(dto.getUserPw()));

        // 이름 수정
        if (dto.getName() != null) member.setName(dto.getName());
        // 주소 수정
        if (dto.getAddress() != null) member.setAddress(dto.getAddress());
        // 연락처 수정
        if (dto.getPhoneNum() != null) member.setPhoneNum(dto.getPhoneNum());
        // 생년월일 수정
        if (dto.getBirthDate() != null) member.setBirthDate(dto.getBirthDate());
        // 성별 수정
        if (dto.getGender() != null) member.setGender(dto.getGender());
        // 내외국인 수정
        if (dto.getForeigner() != null) member.setForeigner(dto.getForeigner());

        generalRepository.save(member);

        System.out.println(member);
    }

    // 일반계정 회원정보 조회
    public GeneralDTO get(String userId) {
        log.info("GeneralMember Get Info");

        GeneralMember member = generalRepository.findByUserId(userId).orElseThrow();

        return ModelMapper.generalEntityToDTO(member);
    }

    // 일반계정 회원탈퇴
    public void deleteId(String userId) {
        log.info("GeneralMember Delete Id");

        GeneralMember member = generalRepository.findByUserId(userId).orElseThrow();

        member.setDelFlag(true);
        generalRepository.save(member);
    }
}
