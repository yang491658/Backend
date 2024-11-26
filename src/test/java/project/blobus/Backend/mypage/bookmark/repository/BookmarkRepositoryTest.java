package project.blobus.Backend.mypage.bookmark.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.member.role.general.entity.GeneralMember;
import project.blobus.Backend.member.role.general.repository.GeneralRepository;
import project.blobus.Backend.mypage.bookmark.entity.Bookmark;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class BookmarkRepositoryTest {
    @Autowired
    private GeneralRepository generalRepository;
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Test
    @DisplayName("즐겨찾기 테스트")
    public void test1() {
        Random random = new Random();

        String[] mainList = {"청년", "기업", "지역"};

        String[] youthList = {"일자리", "구인", "주거", "금유", "교육", "창업"};
        String[] enterpriseList = {"기업1", "기업2", "기업3"};
        String[] resouceList = {"문화", "관광", "지원"};


        Map<String, String[]> subMap = new HashMap<>();
        subMap.put("청년", youthList);
        subMap.put("기업", enterpriseList);
        subMap.put("지역", resouceList);

        for (int i = 1; i <= 20; i++) {
            String userId = "test" + (random.nextInt(3) + 1) + "@test.com";
            GeneralMember member = generalRepository.findByUserId(userId)
                    .orElseThrow();

            String mainCategory = mainList[random.nextInt(mainList.length)];

            String[] subList = subMap.get(mainCategory);
            String subCategory = subList[random.nextInt(subList.length)];

            Long targetId = random.nextInt(10) + 1L;

            Bookmark bookmark = Bookmark.builder()
                    .member(member)
                    .mainCategory(mainCategory)
                    .subCategory(subCategory)
                    .targetId(targetId)
                    .atTime(LocalDateTime.now())
                    .build();
            bookmarkRepository.save(bookmark);
        }
    }
}