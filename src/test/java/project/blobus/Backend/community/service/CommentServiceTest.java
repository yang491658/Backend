package project.blobus.Backend.community.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.community.dto.CommentDTO;

@SpringBootTest
class CommentServiceTest {
    private static final Logger log = LoggerFactory.getLogger(CommentServiceTest.class);
    @Autowired
    private CommentService service;

    @Test
    @DisplayName("댓글 조회 테스트")
    public void test1() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<CommentDTO> result = service.getList(pageRequestDTO, 100L);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("댓글 등록 테스트")
    public void test2() {
        String name = "김테스트";
        String userId = "test@test.com";

        for (int i = 1; i <= 3; i++) {
            CommentDTO dto = CommentDTO.builder()
                    .authorId(userId)
                    .authorName(name)
                    .authorEmail(userId)
                    .content("댓글 등록 테스트" + i)
                    .postId(103L)
                    .build();

            if (i == 3) {
                dto.setVisibility(true);
            }

            service.register(dto);
        }
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    public void test4() {
        CommentDTO dto = CommentDTO.builder()
                .id(2L)
                .content("댓글 수정 테스트")
                .build();

        service.modify(dto);
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void test5() {
        service.remove(1L);
    }
}
