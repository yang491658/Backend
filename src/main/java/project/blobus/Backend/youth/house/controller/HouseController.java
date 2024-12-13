package project.blobus.Backend.youth.house.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.blobus.Backend.youth.house.dto.HouseDTO;
import project.blobus.Backend.youth.house.dto.PageRequestDTO;
import project.blobus.Backend.youth.house.dto.PageResponseDTO;
import project.blobus.Backend.youth.house.service.HouseService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/youth/house")
public class HouseController {
    // @RequiredArgsConstructor : 사용하기 때문에 final 을 붙여야함 / @autowired 대신 사용
    private final HouseService service;

    // BLOBUS > 청년관 > 주거
    // 1.정책현황
    // 정책현황 - 생성

    // 정책현황 - 리스트
    @GetMapping("/policyList")
    public PageResponseDTO<HouseDTO> policyList(PageRequestDTO pageRequestDTO,
                                                @RequestParam(required = false) String policyStsType,
                                                @RequestParam(required = false) String searchTerm,
                                                @RequestParam(required = false) String filterType) {
        log.info("HouseController - policyList 호출 : --------------------------- ");
        return service.getPolicyList(pageRequestDTO, policyStsType, searchTerm, filterType);
    }

    // 정책현황 - 리스트 상세
    @GetMapping("/policyRead/{policyId}")
    public HouseDTO policyDetail(@PathVariable(name="policyId") Long policyId) {
        log.info("HouseController - policyDetail 호출 : --------------------------- ");
        log.info("policyId : " + policyId);
        return service.getPolicyDetail(policyId);
    }

    // 정책현황 - 수정
    @PostMapping("/policyRead/{policyId}")
    public Map<String, String> policyModify(@PathVariable(name="policyId") Long policyId,
                                            @RequestBody HouseDTO houseDTO){
        log.info("HouseController - policyModify 호출 : --------------------------- ");
        log.info(houseDTO.toString());
        houseDTO.setPolicyId(policyId);
        service.policyModify(houseDTO);
        return Map.of("result", "SUCCESS");
    }

    // 정책현황 - 삭제
    @DeleteMapping("/policyRead/{policyId}")
    public Map<String, String> policyRemove(@PathVariable(name = "policyId") Long policyId) {
        log.info("HouseController - policyRemove 호출 : --------------------------- ");
        log.info("policyId : " + policyId);
        service.policyRemove(policyId);
        return Map.of("result", "SUCCESS");
    }
}
