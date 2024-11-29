package project.blobus.Backend.member.role.business.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.member.role.business.dto.BusinessDTO;
import project.blobus.Backend.member.role.common.entity.MemberRole;

@SpringBootTest
class BusinessServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BusinessServiceTest.class);
    @Autowired
    private BusinessService memberService;

    @Test
    @DisplayName("기업계정 회원가입 테스트")
    public void registerTest() {
        BusinessDTO dto = BusinessDTO.builder()
                .userId("123-12-12345")
                .userPw("qwerQWER1234!@#$")
                .name("(주)테스트")
                .phoneNum("01012345678")
                .email("asdf@asdf.com")
                .address("부산광역시-해운대구")
                .roleName(String.valueOf(MemberRole.BUSINESS))
                .build();
        Long id = memberService.register(dto);

        if (id > 0L)
            log.info("가입 완료");
        else
            log.error("가입 실패");
    }

    @Test
    @DisplayName("기업계정 회원가입 - 중복 확인")
    public void duplicateTest() {
        String userId1 = "123-12-12345";
        String userId2 = "321-21-54321";

        if (memberService.duplicate(userId1))
            log.info(userId1 + " : 가입 불가능 (테스트 성공)");
        else
            log.error(userId1 + " : 가입 가능 (테스트 실패)");

        if (memberService.duplicate(userId2))
            log.info(userId2 + " : 가입 가능 (테스트 성공)");
        else
            log.error(userId2 + " : 가입 불가능 (테스트 실패)");
    }
}