package project.blobus.Backend.temp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.temp.entity.TempYouthStartupPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class TempYouthStartupPolicyRepositoryTest {
    @Autowired
    private YouthStartupPolicyRepository repository;

    @Test
    @DisplayName("청년 창업 정책")
    public void test() {
        Random random = new Random();

        int max = 30;
        for (int i = 1; i <= max; i++) {
            LocalDate date = LocalDate.now().minusDays(max - i);
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            TempYouthStartupPolicy temp = TempYouthStartupPolicy.builder()
                    .programName("창업정책" + i)
                    .overview("정책개요" + i)
                    .supportAmount("상세혜텍" + i)
                    .supportType("지원형태" + i)
                    .ageRequirement("연령조건" + i)
                    .startupStatus("창업상태" + i)
                    .locationRequirement(randomAdress(random))
                    .applicationPeriodStart(date.minusDays(random.nextInt(10)))
                    .applicationPeriodEnd(date.plusDays(random.nextInt(10)))
                    .applicationMethod("신청방법" + i)
                    .requiredDocuments("필요서류" + i)
                    .mentoringContent("멘토링" + i)
                    .workspaceSupport("창업공간지원" + i)
                    .postProgramSupport("후속지원정보" + i)
                    .successSupport("창업후지원" + i)
                    .contactInfo("문의처" + i)
                    .contactEmail("이메일" + i)
                    .contactPhone("연락처" + i)
                    .referenceMaterials("참고링크" + i)
                    .businessPlanTips("사업계획서" + i)
                    .createdAt(dateTime)
                    .updatedAt(dateTime.plusDays(random.nextInt(max + 1 - i)))
                    .build();
            repository.save(temp);
        }
    }

    private String randomAdress(Random random) {
        String city = "부산광역시";
        String[] districts = {"강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구",
                "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"};
        String district = districts[random.nextInt(districts.length)];
        return city + "-" + district;
    }
}