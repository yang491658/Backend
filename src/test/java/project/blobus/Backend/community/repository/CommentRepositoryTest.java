package project.blobus.Backend.community.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.community.entity.CommuntiyComment;

import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 임시 등록")
    public void test() {
        Random random = new Random();

        int max = 100;
        for (int i = 1; i <= max; i++) {
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            int authorNum = (random.nextInt(3) + 1);

            for (int j = 1; j <= random.nextInt(5) + 1; j++) {
                CommuntiyComment communtiyComment = CommuntiyComment.builder()
                        .content("임시 댓글" + j)
                        .authorId("test" + authorNum + "@test.com")
                        .authorName("테스트" + authorNum)
                        .visibility(random.nextInt(2) == 0)
                        .createdAt(dateTime)
                        .updatedAt(dateTime.plusDays(random.nextInt(max + 1 - i)))
                        .post(postRepository.findById((long) i).orElseThrow())
                        .build();

                commentRepository.save(communtiyComment);
            }
        }
    }
}