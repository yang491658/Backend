package project.blobus.Backend.member.member.business.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.member.member.business.dto.BusinessDTO;
import project.blobus.Backend.member.member.common.entity.MemberRole;

@SpringBootTest
class BusinessServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BusinessServiceTest.class);
    @Autowired
    private BusinessService memberService;

    @Test
    @DisplayName("기업계정 회원가입 테스트")
    public void registerTest() {
        BusinessDTO businessDTO = BusinessDTO.builder()
                .userId("123-12-12345")
                .userPw("qwerQWER1234!@#$")
                .name("(주)테스트")
                .phoneNum("01012345678")
                .email("asdf@asdf.com")
                .address("부산광역시-해운대구")
                .roleName(String.valueOf(MemberRole.BUSINESS))
                .build();
        memberService.register(businessDTO);
    }

    @Test
    @DisplayName("기업계정 회원가입 - 중복 확인")
    public void duplicateTest() {
        String userId1 = "123-12-12345";
        String userId2 = "321-21-54321";

        log.info(String.valueOf(memberService.duplicate(userId1)));
        log.info(String.valueOf(memberService.duplicate(userId2)));
    }
}