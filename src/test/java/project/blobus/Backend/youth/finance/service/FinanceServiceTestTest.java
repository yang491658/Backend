//package project.blobus.Backend.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import project.blobus.Backend.youth.finance.FinanceDTOTest;
//import project.blobus.Backend.youth.finance.FinanceServiceTest;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//public class FinanceServiceTestTest {
//
//    @Autowired
//    private FinanceServiceTest financeServiceTest;
//
//    @Test
//    public void testSearchPagedPolicies() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<FinanceDTOTest> result = financeServiceTest.searchPagedPolicies("청년", pageable);
//
//        assertNotNull(result);
//        assertTrue(result.getContent().size() <= 10); // 최대 10개씩 가져오기
//        result.getContent().forEach(policy -> {
//            assertTrue(policy.getTitle().contains("청년") || policy.getOverview().contains("청년"));
//        });
//    }
//}
