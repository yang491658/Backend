package project.blobus.Backend.youth.education;

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
@RequestMapping("/api/youth/education")
public class EducationController{

    private final EducationService educationService;

    // 페이징된 정책게시물 가져오기
    @GetMapping("/paged-policies")
    public Page<EducationDTO> getPagedPolicies(
            @RequestParam(defaultValue = "0") int page,           // 페이지 번호
            @RequestParam(defaultValue = "10") int size,          // 페이지 크기
            @RequestParam(defaultValue = "") String keyword,      // 검색어 (기본값: 빈 문자열)
            @RequestParam(defaultValue = "상태전체") String progress,  // 기본값 "유형전체"
            @RequestParam(defaultValue = "유형전체") String category  // 기본값 "유형전체"

    ) {
        Pageable pageable = PageRequest.of(page , size);                         // 페이징 객체 생성
        return educationService.getPagedPolicies(keyword, progress, category, pageable); // 서비스 호출

    }

    // 모든 정책 가져오기
    @GetMapping("/policies")
    public List<EducationDTO> getAllPolicies() {
        return educationService.getAllPolicies();
    }

    // 특정 ID의 정책 가져오기
    @GetMapping("/policies/{id}")
    public EducationDTO getPolicyById(@PathVariable Integer id) {
        return educationService.getPolicyById(id);
    }

    @PostMapping("/policies/{id}")
    public Map<String, String> modify(@PathVariable Integer id, @RequestBody EducationDTO educationDTO) {
        educationDTO.setPolicyId(id);

        log.info("프론트에서 수정되기 원하는 정책 ID " + id);

        log.info("Before Modify:" + educationDTO);

        educationService.modify(educationDTO);

        log.info("After Modify:" + educationDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/policies/{id}")
    public Map<String, String> remove(@PathVariable("id") Integer id) {
        educationService.remove(id);
        return Map.of("RESULT", "SUCCESS");
    }
}
