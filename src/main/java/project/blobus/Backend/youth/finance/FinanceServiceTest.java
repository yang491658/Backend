package project.blobus.Backend.youth.finance;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceServiceTest {

    private final FinanceRepositoryTest financeRepositoryTest;

    public FinanceServiceTest(FinanceRepositoryTest financeRepositoryTest) {
        this.financeRepositoryTest = financeRepositoryTest;
    }

    // 모든 정책 가져오기
    public List<FinanceDTOTest> getAllPolicies() {
        return financeRepositoryTest.findAll()
                .stream()
                .map(FinanceDTOTest::new) // Entity -> DTO 변환
                .collect(Collectors.toList());
    }

    // 특정 ID의 정책 가져오기
    public FinanceDTOTest getPolicyById(Integer id) {
        return financeRepositoryTest.findById(id)
                .map(FinanceDTOTest::new)
                .orElse(null);
    }
}
