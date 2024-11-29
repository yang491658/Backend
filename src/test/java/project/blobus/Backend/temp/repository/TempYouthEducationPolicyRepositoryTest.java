package project.blobus.Backend.temp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.temp.entity.TempYouthEducationPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class TempYouthEducationPolicyRepositoryTest {
    @Autowired
    private YouthEducationPolicyRepository repository;

    @Test
    @DisplayName("청년 교육 정책")
    public void test() {
        Random random = new Random();

        int max = 30;
        for (int i = 1; i <= max; i++) {
            LocalDate date = LocalDate.now().minusDays(max - i);
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            TempYouthEducationPolicy temp = TempYouthEducationPolicy.builder()
                    .programName("교육정책" + i)
                    .overview("정책개요" + i)
                    .supportAmount("상세혜텍" + i)
                    .supportType("교육형태" + i)
                    .ageRequirement("연령조건" + i)
                    .academicRequirement("학력/직업" + i)
                    .applicationPeriodStart(date.minusDays(random.nextInt(10)))
                    .applicationPeriodEnd(date.plusDays(random.nextInt(10)))
                    .applicationMethod("신청방법" + i)
                    .requiredDocuments("필요서류" + i)
                    .curriculum("교육내용" + i)
                    .schedule("교육일정")
                    .location(randomAdress(random))
                    .duration("교육기간" + i)
                    .selectionCriteria("선발기준" + i)
                    .postProgramBenefits("지원후혜택" + i)
                    .contactInfo("문의처" + i)
                    .contactEmail("이메일" + i)
                    .contactPhone("연락처" + i)
                    .referenceMaterials("참고링크" + i)
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