package project.blobus.Backend.youth.finance.respository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.youth.finance.FinanceEntityTest;
import project.blobus.Backend.youth.finance.FinanceRepositoryTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FinanceRepositoryTestTest {

    @Autowired
    private FinanceRepositoryTest financeRepositoryTest;

    // 더미 데이터 1개만 생성하는 test
    @Test
    public void createFinancePolicyTest() throws Exception {
        // 날짜 포맷
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse("2024-01-01");
        Date endDate = dateFormat.parse("2024-12-31");

        // 더미 데이터 생성
        FinanceEntityTest policy = new FinanceEntityTest();
        policy.setTitle("청년 우리는 내일도 절망절망");
        policy.setOverview("청년을 위한 주택 희망");
        policy.setApplicationPeriodStart(startDate);
        policy.setApplicationPeriodEnd(endDate);
        policy.setContactInfo("주택공사4");
        policy.setContactPhone("010-1234-1234");

        // 데이터 저장
        FinanceEntityTest savedPolicy = financeRepositoryTest.save(policy);

//        // 저장된 데이터가 올바른지 확인
//        Optional<FinanceEntityTest> retrievedPolicy = financeRepositoryTest.findById(savedPolicy.getPolicyId());
//
//        assertThat(retrievedPolicy).isPresent(); // 데이터가 존재하는지 확인
//        assertThat(retrievedPolicy.get().getTitle()).isEqualTo("청년 주택 지원 정책4");
//        assertThat(retrievedPolicy.get().getOverview()).isEqualTo("청년을 위한 주택 지원 정책4");
//        assertThat(retrievedPolicy.get().getApplicationPeriodStart()).isEqualTo(startDate);
//        assertThat(retrievedPolicy.get().getApplicationPeriodEnd()).isEqualTo(endDate);
//        assertThat(retrievedPolicy.get().getContactInfo()).isEqualTo("주택공사4");
//        assertThat(retrievedPolicy.get().getContactPhone()).isEqualTo("010-1234-1234");
    }

    // 반복문으로 더미데이터 많이 만들기 test
    @Test
    public void createFinancePolicyManyTest() throws Exception {
        // 날짜 포맷
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse("2024-01-01");
        Date endDate = dateFormat.parse("2024-12-31");

        // 50개의 더미 데이터 생성
        for (int i = 0; i <= 50; i++) {
            // 더미 데이터 생성
            FinanceEntityTest policy = new FinanceEntityTest();
            policy.setTitle("청년 주택 지원 정책 " + i);
            policy.setOverview("청년을 위한 주택 지원 정책 " + i);
            policy.setApplicationPeriodStart(startDate);
            policy.setApplicationPeriodEnd(endDate);
            policy.setContactInfo("주택공사 " + i);
            policy.setContactPhone("010-1234-" + String.format("%04d", i)); // 전화번호 마지막 4자리 변경

            // 데이터 저장
            financeRepositoryTest.save(policy);
        }
    }


    @Test
    public void testReadByPolicyId() {
        // 조회할 정책 ID
        Integer policyId = 2; // 저장된 policy_id 값 중 하나를 입력

        // 데이터 조회
        Optional<FinanceEntityTest> optionalPolicy = financeRepositoryTest.findById(policyId);

        // 데이터가 존재하는지 확인
        if (optionalPolicy.isPresent()) {
            FinanceEntityTest policy = optionalPolicy.get();
            System.out.println("조회된 정책 정보: ");
            System.out.println("Policy ID: " + policy.getPolicyId());
            System.out.println("Title: " + policy.getTitle());
            System.out.println("Overview: " + policy.getOverview());
            System.out.println("Application Period Start: " + policy.getApplicationPeriodStart());
            System.out.println("Application Period End: " + policy.getApplicationPeriodEnd());
            System.out.println("Contact Info: " + policy.getContactInfo());
            System.out.println("Contact Phone: " + policy.getContactPhone());
        } else {
            System.out.println("Policy ID " + policyId + "에 해당하는 데이터가 없습니다.");
        }
    }
}
