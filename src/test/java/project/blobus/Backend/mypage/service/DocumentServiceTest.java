package project.blobus.Backend.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
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
        String boardType = null;
        String boardCategory = null;

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, boardCategory);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("자유게시판 조회 테스트")
    public void test2() {
        String userId = "test1@test.com";
        String boardType = "FREE";
        String boardCategory = null;

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, boardCategory);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("건의게시판 조회 테스트")
    public void test3() {
        String userId = "test1@test.com";
        String boardType = "SUGGEST";
        String boardCategory = null;

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, boardCategory);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("전체게시판 조회 테스트 - 청년탭")
    public void test4() {
        String userId = "test1@test.com";
        String boardType = null;
        String boardCategory = "YOUTH";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, boardCategory);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }

    @Test
    @DisplayName("자유게시판 조회 테스트 - 기업탭")
    public void test5() {
        String userId = "test1@test.com";
        String boardType = "FREE";
        String boardCategory = "ENTERPRISE";

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<DocumentdDTO> result = service.getList(pageRequestDTO, userId, boardType, boardCategory);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }
}