package project.blobus.Backend.youth.finance;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/youth/finance")
public class FinanceControllerTest {

    private final FinanceServiceTest financeServiceTest;

    public FinanceControllerTest(FinanceServiceTest financeServiceTest) {
        this.financeServiceTest = financeServiceTest;
    }

    // 모든 정책 가져오기
    @GetMapping("/policies")
    public List<FinanceDTOTest> getAllPolicies() {
        return financeServiceTest.getAllPolicies();
    }

    // 특정 ID의 정책 가져오기
    @GetMapping("/policies/{id}")
    public FinanceDTOTest getPolicyById(@PathVariable Integer id) {
        return financeServiceTest.getPolicyById(id);
    }
}
