package project.blobus.Backend.main.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import project.blobus.Backend.main.dto.AllSearchDTO;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AllSearchRepositoryTest {

    @Autowired
    private AllSearchRepository allSearchRepository;

//    @BeforeEach
//    public void setUp() {
//        // 샘플 데이터 삽입
//        HousingPolicy housingPolicy = new HousingPolicy("Housing Policy 1");
//        WelfarePolicy welfarePolicy = new WelfarePolicy("Welfare Policy 1");
//        EducationPolicy educationPolicy = new EducationPolicy("Education Policy 1");
//
//        allSearchRepository.save(housingPolicy);
//        allSearchRepository.save(welfarePolicy);
//        allSearchRepository.save(educationPolicy);
//    }

    @Test
    @Transactional
    public void testGetPolicyTitles() {
        // Act: get the list of policy titles from the repository
//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title")));

        int page = 2;
        int end = page * 10;
        int start = (end - 9) - 1;
        String search = "%청년%";
        List<AllSearchDTO> policyTitlesPage = allSearchRepository.getPolicyTitles(search, start);
        for(AllSearchDTO dto : policyTitlesPage) {
            System.out.println(dto.getTitle());
        }
    }
}
