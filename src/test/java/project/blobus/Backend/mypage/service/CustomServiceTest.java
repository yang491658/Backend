package project.blobus.Backend.mypage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.member.basic.dto.PageRequestDTO;
import project.blobus.Backend.member.basic.dto.PageResponseDTO;
import project.blobus.Backend.mypage.dto.CustomDTO;

@SpringBootTest
class CustomServiceTest {
    private static final Logger log = LoggerFactory.getLogger(CustomServiceTest.class);
    @Autowired
    private CustomService service;

    @Test
    @DisplayName("커스텀 조회 테스트")
    public void test1() {
        String address = "부산광역시-강서구";
        String yListStr = "일자리/주거/창업";
        String eListStr = "기업2/기업3";
        String rListStr = "문화/지원";
        String kListStr = "기준/신청";
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<CustomDTO> result = service.getList(pageRequestDTO, address, yListStr, eListStr, rListStr, kListStr);
        result.getDtoList().forEach(dto -> log.info(dto.toString()));
    }
}