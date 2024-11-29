package project.blobus.Backend.temp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.temp.entity.TempCommunitySuggestBoard;

import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class TempCommunitySuggestBoardRepositoryTest {
    @Autowired
    private CommunitySuggestBoardRepository repository;

    @Test
    @DisplayName("게시판 임시 등록")
    public void test() {
        Random random = new Random();

        int max = 20;
        for (int i = 1; i <= max; i++) {
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            TempCommunitySuggestBoard board = TempCommunitySuggestBoard.builder()
                    .title("건의게시글" + i)
                    .content("건의 내용 " + i)
                    .boardCategory(random.nextInt(2) == 0 ? "YOUTH" : "ENTERPRISE")
                    .visibility(random.nextInt(2) == 0 ? "PUBLIC" : "PRIVATE")
                    .authorId("test" + (random.nextInt(3) + 1) + "@test.com")
                    .createdAt(dateTime)
                    .updatedAt(dateTime.plusDays(random.nextInt(max + 1 - i)))
                    .build();
            repository.save(board);
        }
    }
}