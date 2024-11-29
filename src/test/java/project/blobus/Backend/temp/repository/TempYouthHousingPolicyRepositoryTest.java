package project.blobus.Backend.temp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.temp.entity.TempYouthHousingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class TempYouthHousingPolicyRepositoryTest {
    @Autowired
    private YouthHousingPolicyRepository repository;

    @Test
    @DisplayName("청년 주거 정책")
    public void test() {
        Random random = new Random();

        int max = 30;
        for (int i = 1; i <= max; i++) {
            LocalDate date = LocalDate.now().minusDays(max - i);
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            TempYouthHousingPolicy temp = TempYouthHousingPolicy.builder()
                    .title("주거정책" + i)
                    .description("상세설명" + i)
                    .startDate(date.minusDays(random.nextInt(10)))
                    .endDate(date.plusDays(random.nextInt(10)))
                    .region(randomAdress(random))
                    .eligibility("지원자격" + i)
                    .supportContent("지원내용" + i)
                    .applicationMethod("신청방법" + i)
                    .contactInfo("문의처" + i)
                    .createdAt(dateTime)
                    .updatedAt(dateTime.plusDays(random.nextInt(max + 1 - i)))
                    .status("정책상태" + i)
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