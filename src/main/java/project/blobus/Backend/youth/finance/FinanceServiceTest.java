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
    public Page<FinanceDTOTest> getPagedPolicies(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // 검색어가 없으면 전체 데이터를 페이징 처리
            return financeRepositoryTest.findAll(pageable)
                    .map(FinanceDTOTest::new);
        }
        // 검색어가 있으면 제목에서 검색
        return financeRepositoryTest.findByTitleContaining(keyword, pageable)
                .map(FinanceDTOTest::new);

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
