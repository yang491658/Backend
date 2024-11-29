package project.blobus.Backend.temp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.temp.entity.TempYouthJobPosting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class TempYouthJobPostingRepositoryTest {
    @Autowired
    private YouthJobPostingRepository repository;

    @Test
    @DisplayName("청년 일자리 구인")
    public void test() {
        Random random = new Random();

        int max = 30;
        for (int i = 1; i <= max; i++) {
            LocalDate date = LocalDate.now().minusDays(max - i);
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            TempYouthJobPosting temp = TempYouthJobPosting.builder()
                    .companyName("회사이름" + i)
                    .jobTitle("모집직무" + i)
                    .jobDescription("직무설명" + i)
                    .jobType("고용형태" + i)
                    .location(randomAdress(random))
                    .salary("급여정보" + i)
                    .qualification("지원자격" + i)
                    .applicationDeadline(date.plusDays(random.nextInt(10)))
                    .contactInfo("문의처" + i)
                    .createdAt(dateTime)
                    .updatedAt(dateTime.plusDays(random.nextInt(max + 1 - i)))
                    .status("구인상태" + i)
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