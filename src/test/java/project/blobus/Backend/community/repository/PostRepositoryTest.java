package project.blobus.Backend.community.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.community.entity.CommunityPost;

import java.time.LocalDateTime;
import java.util.Random;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private PostRepository repository;

    @Test
    @DisplayName("게시판 임시 등록")
    public void test() {
        Random random = new Random();
        String[] categoryList = {"청년",  "지역"};

        int max = 100;
        for (int i = 1; i <= max; i++) {
            LocalDateTime dateTime = LocalDateTime.now().minusDays(max - i);

            int authorNum = (random.nextInt(3) + 1);

            CommunityPost communityPost = CommunityPost.builder()
                    .authorId("test" + authorNum + "@test.com")
                    .authorName("테스트" + authorNum)
                    .authorEmail("test" + authorNum + "@test.com")
                    .boardType(random.nextInt(2) == 0 ? "자유" : "건의")
                    .category(categoryList[random.nextInt(2)])
                    .title("임시 제목" + i)
                    .content("이 편지는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고"
                            + "\n지금은 당신에게로 옮겨진 이 편지는 4일 안에 당신 곁을 떠나야 합니다."
                            + "\n이 편지를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다."
                            + "\n복사를 해도 좋습니다. 혹 미신이라 하실지 모르지만 사실입니다.")
                    .createdAt(dateTime)
                    .updatedAt(dateTime.plusDays(random.nextInt(max + 1 - i)))
                    .build();

            if (communityPost.getBoardType() == "건의") communityPost.setVisibility(random.nextInt(2) == 0);

            repository.save(communityPost);
        }
    }
}