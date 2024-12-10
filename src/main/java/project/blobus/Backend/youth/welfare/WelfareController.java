package project.blobus.Backend.youth.welfare;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/youth/welfare")
public class WelfareController {

    private final WelfareService welfareService;

    // 페이징된 정책게시물 가져오기
    @GetMapping("/paged-policies")
    public Page<WelfareDTO> getPagedPolicies(
            @RequestParam(defaultValue = "0") int page,           // 페이지 번호
            @RequestParam(defaultValue = "10") int size,          // 페이지 크기
            @RequestParam(defaultValue = "") String keyword,      // 검색어 (기본값: 빈 문자열)
            @RequestParam(defaultValue = "상태전체") String progress,  // 기본값 "제목+내용"
            @RequestParam(defaultValue = "유형전체") String category  // 기본값 "제목+내용"
    ) {
        Pageable pageable = PageRequest.of(page , size);                         // 페이징 객체 생성
        return welfareService.getPagedPolicies(keyword, progress, category, pageable); // 서비스 호출
    }

    // 모든 정책 가져오기
    @GetMapping("/policies")
    public List<WelfareDTO> getAllPolicies() {
        return welfareService.getAllPolicies();
    }

    // 특정 ID의 정책 가져오기
    @GetMapping("/policies/{id}")
    public WelfareDTO getPolicyById(@PathVariable Integer id) {
        log.info("id :" + id);
        return welfareService.getPolicyById(id);
    }

    @PostMapping("/policies/{id}")
    public Map<String, String> modify(@PathVariable Integer id, @RequestBody WelfareDTO welfareDTO) {
        welfareDTO.setPolicyId(id);

        log.info("프론트에서 수정되기 원하는 정책 ID " + id);

        log.info("Before Modify:" + welfareDTO);

        welfareService.modify(welfareDTO);

        log.info("After Modify:" + welfareDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/policies/{id}")
    public Map<String, String> remove(@PathVariable("id") Integer id) {
        welfareService.remove(id);
        return Map.of("RESULT", "SUCCESS");
    }
}
