package project.blobus.Backend.member.role.general.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.member.role.common.entity.MemberRole;
import project.blobus.Backend.member.role.general.dto.GeneralDTO;

import java.time.LocalDate;

@SpringBootTest
class GeneralServiceTest {
    private static final Logger log = LoggerFactory.getLogger(GeneralServiceTest.class);
    @Autowired
    private GeneralService memberService;

    @Test
    @DisplayName("일반계정 회원가입")
    public void registerTest() {
        for (int i = 1; i <= 3; i++) {
            GeneralDTO dto = GeneralDTO.builder()
                    .userId("test" + i + "@test.com")
                    .userPw("qwerQWER1234!@#$")
                    .name("테스트" + i)
                    .phoneNum("010" + (11111111 * i))
                    .address("부산광역시-해운대구")
                    .birthDate(LocalDate.of(1980 + 10 * i, i, i))
                    .gender("M")
                    .foreigner(false)
                    .roleName(String.valueOf(MemberRole.GENERAL))
                    .build();
            Long id = memberService.register(dto);

            if (id > 0L)
                log.info("가입 완료");
            else
                log.error("가입 실패");
        }
    }

    @Test
    @DisplayName("일반계정 회원가입 - 연락처 중복")
    public void registerFailTest() {
        GeneralDTO dto = GeneralDTO.builder()
                .userId("test4@test.com")
                .userPw("qwerQWER1234!@#$")
                .name("테스트4")
                .phoneNum("01011111111")
                .address("부산광역시-해운대구")
                .birthDate(LocalDate.of(1999, 9, 9))
                .gender("M")
                .foreigner(true)
                .roleName(String.valueOf(MemberRole.GENERAL))
                .build();
        Long id = memberService.register(dto);

        if (id == 0L)
            log.info("중복된 연락처");
        else
            log.error("가입 완료");
    }

    @Test
    @DisplayName("일반계정 회원가입 - 중복 확인")
    public void duplicateTest() {
        String userId1 = "test1@test.com";
        String userId2 = "test4@test.com";

        if (memberService.duplicate(userId1))
            log.info(userId1 + " : 가입 불가능 (테스트 성공)");
        else
            log.error(userId1 + " : 가입 가능 (테스트 실패)");

        if (memberService.duplicate(userId2))
            log.info(userId2 + " : 가입 가능 (테스트 성공)");
        else
            log.error(userId2 + " : 가입 불가능 (테스트 실패)");
    }

    @Test
    @DisplayName("일반계정 회원가입 - 메일 전송")
    void sendEmailTest() {
        Long code = memberService.sendEmail("bell4916@naver.com");

        log.info("CODE : " + code);
    }

    @Test
    @DisplayName("일반계정 아이디 찾기")
    void findTest() {
        String userId = memberService.find("테스트3", "01033333333");

        log.info("검색 결과 : " + userId);
    }

    @Test
    @DisplayName("일반계정 비밀번호 찾기(변경) + 회원정보 수정")
    void mofigyPwTest() {
        GeneralDTO dto = GeneralDTO.builder()
                .userId("test3@test.com")
                .userPw("asdf")
                .name("테스트3")
                .address("서울특별시-강남구")
                .birthDate(LocalDate.of(1999, 9, 9))
                .gender("F")
                .foreigner(true)
                .build();
        memberService.modify(dto);
    }

    @Test
    @DisplayName("일반계정 회원정보 조회")
    void getTest() {
        for (int i = 1; i <= 3; i++) {
            String userId = "test" + i + "@test.com";
            GeneralDTO dto = memberService.get(userId);

            log.info(dto.toString());
        }
    }

    @Test
    @DisplayName("일반계정 회원탈퇴")
    void deleteTest() {
        String userId = "test1@test.com";
        memberService.deleteId(userId);
    }
}