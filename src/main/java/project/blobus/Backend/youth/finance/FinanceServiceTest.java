package project.blobus.Backend.youth.finance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceServiceTest {

    private final FinanceRepositoryTest financeRepositoryTest;

    public FinanceServiceTest(FinanceRepositoryTest financeRepositoryTest) {
        this.financeRepositoryTest = financeRepositoryTest;
    }

    // 페이징된 정책 목록 가져오기
    public Page<FinanceDTOTest> getPagedPolicies(String keyword, String category, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // 검색어가 없으면 전체 데이터를 페이징 처리
            return financeRepositoryTest.findAll(pageable)
                    .map(FinanceDTOTest::new);
        }
        // 카테고리에 따라 다른 검색 수행
        switch (category) {
            case "제목":
                return financeRepositoryTest.findByTitleContaining(keyword, pageable)
                        .map(FinanceDTOTest::new);
            case "내용":
                return financeRepositoryTest.findByOverviewContaining(keyword, pageable)
                        .map(FinanceDTOTest::new);
            default: // "전체"
                return financeRepositoryTest.findByTitleContainingOrOverviewContaining(keyword, keyword, pageable)
                        .map(FinanceDTOTest::new);
        }

//        return financeRepositoryTest.findAll(pageable)
//                .map(FinanceDTOTest::new); // Page<Entity> -> Page<DTO>로 변환
    }

    // 모든 정책 가져오기
    public List<FinanceDTOTest> getAllPolicies() {
        List<FinanceDTOTest> policies = financeRepositoryTest.findAll()
                .stream()
                .map(FinanceDTOTest::new)
                .collect(Collectors.toList());
        System.out.println("Retrieved " + policies.size() + " policies");
        return policies;
    }

    // 특정 ID의 정책 가져오기
    public FinanceDTOTest getPolicyById(Integer id) {
        return financeRepositoryTest.findById(id)
                .map(FinanceDTOTest::new)
                .orElseThrow(() -> new RuntimeException("Policy not found for ID: " + id));
    }
}
