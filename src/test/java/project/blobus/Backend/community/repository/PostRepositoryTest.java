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
        // 1부터 10까지의 authorId로 더미 데이터를 생성
        for (int i = 1; i < 100; ++i) {
            // authorId가 null이 아닌지 확인하기 위해 1부터 10까지 순차적으로 설정
            Post post = Post.builder()
                    .authorId(String.valueOf((long) (i % 10))) // authorId를 1부터 10까지 순차적으로 설정
                    .title("게시글 제목.." + i)
                    .content("게시글 내용..." + i)
                    .boardType(i % 2 == 0 ? Post.BoardType.FREE : Post.BoardType.SUGGESTION)
                    .userType(i % 2 == 0 ? Post.UserType.YOUTH : Post.UserType.CORPORATE)
                    .createdAt(LocalDateTime.now()) // 생성일자
                    .build();

            System.out.println(post);
            postRepository.save(post);  // 게시글 저장
        }
    }
}