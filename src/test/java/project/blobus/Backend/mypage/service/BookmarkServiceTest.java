package project.blobus.Backend.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.mypage.dto.BookmarkDTO;

@SpringBootTest
class BookmarkServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BookmarkServiceTest.class);
    @Autowired
    private BookmarkService service;

    @Test
    @DisplayName("즐겨찾기 여부 확인 테스트")
    public void test1() {
        String userId = "test2@test.com";
        String main = "청년";
        String sub = "일자리";
        Long targetId = 1L;
        log.info(String.valueOf(service.check(userId, main, sub, targetId)));
    }

    @Test
    @DisplayName("즐겨찾기 변경 테스트")
    public void test2() {
        String userId = "test2@test.com";
        String main = "청년";
        String sub = "일자리";
        Long targetId = 1L;
        service.change(userId, main, sub, targetId);
    }

    @Test
    @DisplayName("즐겨찾기 목록 조회 테스트 - 전체")
    public void test3() {
        String userId = "test2@test.com";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<BookmarkDTO> result = service.getList(pageRequestDTO, userId, "");
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("즐겨찾기 목록 조회 테스트 - 청년")
    public void test4() {
        String userId = "test2@test.com";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<BookmarkDTO> result = service.getList(pageRequestDTO, userId, "청년");
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }
}