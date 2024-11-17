package project.blobus.Backend.member.member.general.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.member.member.common.entity.MemberRole;
import project.blobus.Backend.member.member.general.dto.GeneralDTO;

import java.time.LocalDate;

@SpringBootTest
class GeneralServiceTest {
    private static final Logger log = LoggerFactory.getLogger(GeneralServiceTest.class);
    @Autowired
    private GeneralService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    public void registerTest() {
        GeneralDTO generalDTO = GeneralDTO.builder()
                .userId("test@test.com")
                .userPw("qwerQWER1234!@#$")
                .name("테스트")
                .phoneNum("01012345678")
                .birthDate(LocalDate.of(1999, 9, 9))
                .gender("M")
                .foreigner(false)
                .address("부산광역시-해운대구")
                .roleName(String.valueOf(MemberRole.GENERAL))
                .build();
        memberService.register(generalDTO);
    }

    @Test
    @DisplayName("회원가입 - 아이디 중복")
    public void duplicateTest() {
        String userId1 = "test@test.com";
        String userId2 = "test2@test.com";


        log.info(String.valueOf(memberService.duplicate(userId1)));
        log.info(String.valueOf(memberService.duplicate(userId2)));
    }

//    @Test
//    @DisplayName("회원정보 수정 테스트")
//    public void test4() {
//        Long enb = 28L;
//        EmployeeDTO employeeDTO = EmployeeDTO.builder()
//                .enb(enb)
//                .empPW("0000")
//                .hireDate(LocalDate.of(2001, 1, 1))
//                .name("홍길동")
//                .gender(false)
//                .phoneNum(1099999999)
//                .email("new@email.com")
//                .companyDTO(companyService.get(501L))
//                .build();
//        employeeService.modify(employeeDTO);
//    }

//    @Test
//    @DisplayName("회원탈퇴 테스트")
//    public void test5() {
//        Long enb = 27L;
//        employeeService.remove(enb);
//    }
}