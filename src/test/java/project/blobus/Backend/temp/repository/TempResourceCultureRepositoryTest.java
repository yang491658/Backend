package project.blobus.Backend.temp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.temp.entity.TempResourceCulture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class TempResourceCultureRepositoryTest {
    @Autowired
    private ResourceCultureRepository repository;

    @Test
    @DisplayName("지역자원 문화")
    public void test() {
        Random random = new Random();
        String[] categoryList = {"문화", "관광", "자원"};

        int max = 30;
        for (int i = 1; i <= max; i++) {
            LocalDate date = LocalDate.now().minusDays(max - i);
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            TempResourceCulture temp = TempResourceCulture.builder()
                    .title("문화이름" + i)
                    .content("내용" + i)
                    .address(randomAdress(random))
                    .imageUUID("이미지" + i)
                    .category(categoryList[random.nextInt(categoryList.length)])
                    .subcategory("서브카테고리" + i)
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