package project.blobus.Backend.community.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.community.entity.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testInsert() {
        for(int i =1; i < 100; ++i){
            Post post = Post.builder()
                    .title("게시글 제목.." + i)
                    .content("게시글 내용..." + i)  // content 추가
                    .authorId((long) (i % 10))  // authorId를 10명으로 제한 (예시)
                    .boardType(i % 2 == 0 ? Post.BoardType.FREE : Post.BoardType.SUGGESTION)  // boardType을 Youth와 Corporate로 나누기
                    .userType(i % 2 == 0 ? Post.UserType.YOUTH : Post.UserType.CORPORATE)  // userType을 Youth와 Corporate로 나누기
                    .createdAt(LocalDateTime.of(2024, 12, 24, 00, 00))  // 생성일자
                    .build();

            // 게시글 저장
            postRepository.save(post);
        }
    }
}