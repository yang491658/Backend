package project.blobus.Backend.youth.education;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/youth/education")
public class EducationControllerTest {

    private final EducationServiceTest educationServiceTest;

    // 페이징된 정책게시물 가져오기
    @GetMapping("/paged-policies")
    public Page<EducationDTOTest> getPagedPolicies(
            @RequestParam(defaultValue = "0") int page,           // 페이지 번호
            @RequestParam(defaultValue = "10") int size,          // 페이지 크기
            @RequestParam(defaultValue = "") String keyword,      // 검색어 (기본값: 빈 문자열)
            @RequestParam(defaultValue = "전체") String category  // 기본값 "전체"
    ) {
        Pageable pageable = PageRequest.of(page , size);                         // 페이징 객체 생성
        return educationServiceTest.getPagedPolicies(keyword, category, pageable); // 서비스 호출
    }

    // 모든 정책 가져오기
    @GetMapping("/policies")
    public List<EducationDTOTest> getAllPolicies() {
        return educationServiceTest.getAllPolicies();
    }

    // 특정 ID의 정책 가져오기
    @GetMapping("/policies/{id}")
    public EducationDTOTest getPolicyById(@PathVariable Integer id) {
        return educationServiceTest.getPolicyById(id);
    }
}
