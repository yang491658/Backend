package project.blobus.Backend.temp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.temp.entity.TempCommunityFreeBoard;

import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class TempCommunityFreeBoardRepositoryTest {
    @Autowired
    private CommunityFreeBoardRepository repository;

    @Test
    @DisplayName("게시판 임시 등록")
    public void test() {
        Random random = new Random();

        int max = 50;
        for (int i = 1; i <= max; i++) {
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            TempCommunityFreeBoard board = TempCommunityFreeBoard.builder()
                    .title("자유게시글" + i)
                    .content("자유 내용 " + i)
                    .boardCategory(random.nextInt(2) == 0 ? "YOUTH" : "ENTERPRISE")
                    .authorId("test" + (random.nextInt(3) + 1) + "@test.com")
                    .createdAt(dateTime)
                    .updatedAt(dateTime.plusDays(random.nextInt(max + 1 - i)))
                    .build();
            repository.save(board);
        }
    }
}