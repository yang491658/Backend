package project.blobus.Backend.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.common.dto.PageRequestDTO;
import project.blobus.Backend.common.dto.PageResponseDTO;
import project.blobus.Backend.mypage.dto.DocumentdDTO;

@SpringBootTest
class DocumentServiceTest {
    private static final Logger log = LoggerFactory.getLogger(DocumentServiceTest.class);
    @Autowired
    private DocumentService service;

    @Test
    @DisplayName("전체게시판 조회 테스트")
    public void test1() {
        String userId = "test1@test.com";
        String boardType = "";
        String category = "";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, category);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("자유게시판 조회 테스트")
    public void test2() {
        String userId = "test1@test.com";
        String boardType = "자유";
        String category = "";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, category);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("건의게시판 조회 테스트")
    public void test3() {
        String userId = "test1@test.com";
        String boardType = "건의";
        String category = "";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, category);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("전체게시판 조회 테스트 - 청년")
    public void test4() {
        String userId = "test1@test.com";
        String boardType = "";
        String category = "청년";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, category);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("자유게시판 조회 테스트 - 기업")
    public void test5() {
        String userId = "test1@test.com";
        String boardType = "자유";
        String category = "기업";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, category);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }
}