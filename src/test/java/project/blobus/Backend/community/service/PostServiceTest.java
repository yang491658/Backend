package project.blobus.Backend.community.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.community.dto.PostDTO;

import java.time.LocalDateTime;

@SpringBootTest
class PostServiceTest {
    private static final Logger log = LoggerFactory.getLogger(PostServiceTest.class);
    @Autowired
    private PostService service;

    @Test
    @DisplayName("커뮤니티 목록 조회 테스트")
    public void test1() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<PostDTO> result = service.getList(pageRequestDTO, "", "", "");
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("커뮤니티 상세 조회 테스트")
    public void test2() {
        Long id = 1L;
        PostDTO dto = service.get(id);
        log.info(dto.toString());
    }

    @Test
    @DisplayName("커뮤니티 게시글 등록 테스트")
    public void test3() {
        for (int i = 1; i <= 3; i++) {
            PostDTO dto = PostDTO.builder()
                    .authorId("test@test.com")
                    .authorName("김테스트")
                    .authorEmail("test@test.com")
                    .boardType(i == 3 ? "건의" : "자유")
                    .category( "청년" )
                    .title("등록 테스트" + i)
                    .content("이 편지는 영국에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고"
                            + "\n지금은 당신에게로 옮겨진 이 편지는 4일 안에 당신 곁을 떠나야 합니다."
                            + "\n이 편지를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다."
                            + "\n복사를 해도 좋습니다. 혹 미신이라 하실지 모르지만 사실입니다.")
                    .toEmail(i == 3)
                    .visibility(i == 3)
                    .commentList(null)
                    .build();

            service.register(dto);
        }
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정 테스트")
    public void test4() {
        PostDTO dto = PostDTO.builder()
                .id(102L)
                .category("지역")
                .title("수정 테스트")
                .content("미안하다 이거 보여주려고 어그로끌었다.."
                        + "\n나루토 사스케 싸움수준 ㄹㅇ실화냐? 진짜 세계관최강자들의 싸움이다..")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        service.modify(dto);
    }

    @Test
    @DisplayName("커뮤니티 게시글 삭제 테스트")
    public void test5() {
        service.remove(101L);
    }
}