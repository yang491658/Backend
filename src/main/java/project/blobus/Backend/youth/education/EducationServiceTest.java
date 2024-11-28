package project.blobus.Backend.youth.education;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationServiceTest {

    private final EducationRepositoryTest educationRepositoryTest;
    private final ModelMapper modelMapper;

    // 페이징된 정책 목록 가져오기
    public Page<EducationDTOTest> getPagedPolicies(String keyword, String category, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // 검색어가 없으면 전체 데이터를 페이징 처리
            return educationRepositoryTest.findAll(pageable)
                    .map(entity -> modelMapper.map(entity, EducationDTOTest.class));
        }
        // 카테고리에 따라 다른 검색 수행
        switch (category) {
            case "제목":
                return educationRepositoryTest.findByTitleContaining(keyword, pageable)
                        .map(entity -> modelMapper.map(entity, EducationDTOTest.class));
            case "내용":
                return educationRepositoryTest.findByOverviewContaining(keyword, pageable)
                        .map(entity -> modelMapper.map(entity, EducationDTOTest.class));
            default: // "전체"
                return educationRepositoryTest.findByTitleContainingOrOverviewContaining(keyword, keyword, pageable)
                        .map(entity -> modelMapper.map(entity, EducationDTOTest.class));
        }
    }

    // 모든 정책 가져오기
    public List<EducationDTOTest> getAllPolicies() {
        List<EducationDTOTest> policies = educationRepositoryTest.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, EducationDTOTest.class))
                .collect(Collectors.toList());
        System.out.println("Retrieved " + policies.size() + " policies");
        return policies;
    }

    // 특정 ID의 정책 가져오기
    public EducationDTOTest getPolicyById(Integer id) {
        return educationRepositoryTest.findById(id)
                .map(entity -> modelMapper.map(entity, EducationDTOTest.class))
                .orElseThrow(() -> new RuntimeException("Policy not found for ID: " + id));
    }
}
