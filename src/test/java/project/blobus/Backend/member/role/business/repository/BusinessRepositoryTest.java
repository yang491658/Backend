package project.blobus.Backend.member.role.business.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.blobus.Backend.member.role.business.entity.BusinessMember;
import project.blobus.Backend.member.role.common.entity.MemberRole;

import java.time.LocalDate;
import java.util.Random;

@SpringBootTest
class BusinessRepositoryTest {
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("기업 회원 임시 등록")
    public void test() {
        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            BusinessMember businessMember = BusinessMember.builder()
                    .userId((111 * i) + "-" + (11 * i) + "-" + (11111 * i))
                    .userPw(passwordEncoder.encode("qwerQWER1234!@#$"))
                    .name("기업" + i)
                    .phoneNum(randomPhoneNum(random))
                    .email(randomEmail(random))
                    .address(randomAdress(random))
                    .joinDate(LocalDate.now())
                    .memberRole(MemberRole.BUSINESS)
                    .build();
            businessRepository.save(businessMember);
        }
    }

    private String randomAdress(Random random) {
        String city = "부산광역시";
        String[] districts = {"강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구",
                "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"};
        String district = districts[random.nextInt(districts.length)];
        return city + "-" + district;
    }

    private String randomPhoneNum(Random random) {
        // 앞 번호
        String[] prefixes = {"011", "016", "017", "018", "019"};
        String prefix = prefixes[random.nextInt(prefixes.length)];
        // 전화번호 생성 (예: 011-xxxx-xxxx)
        StringBuilder phoneNum = new StringBuilder(prefix);
        // 첫 번째 4자리
        for (int i = 0; i < 4; i++) {
            phoneNum.append(random.nextInt(10)); // 0-9
        }
        // 두 번째 4자리
        for (int i = 0; i < 4; i++) {
            phoneNum.append(random.nextInt(10)); // 0-9
        }
        // 앞 번호와 8자리 숫자를 포함한 전화번호를 long으로 변환하여 반환
        return phoneNum.toString();
    }

    private String randomEmail(Random random) {
        String[] domains = {"naver.com", "gmail.com", "nate.com", "yahoo.co.kr"};
        StringBuilder randomAlpha = new StringBuilder();

        // 랜덤 알파벳 4글자 생성
        for (int i = 0; i < 4; i++) {
            randomAlpha.append((char) (random.nextInt(26) + 'a'));
        }
        // 랜덤 4자리 숫자 생성
        int randomNum = random.nextInt(9000) + 1000; // 1000 ~ 9999 범위의 숫자 생성
        // 도메인 선택
        String domain = domains[random.nextInt(domains.length)];
        // 이메일 생성 (알파벳4자리 + 숫자4자리 + 도메인)
        return randomAlpha + String.valueOf(randomNum) + "@" + domain;
    }
}
