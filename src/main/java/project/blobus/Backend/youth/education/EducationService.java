package project.blobus.Backend.youth.education;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    // 페이징된 정책 목록 가져오기
    public Page<EducationDTO> getPagedPolicies(String keyword, String category, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // 검색어가 없으면 전체 데이터를 페이징 처리
            return educationRepository.findAll(pageable)
                    .map(entity -> modelMapper.map(entity, EducationDTO.class));
        }
        // 카테고리에 따라 다른 검색 수행
        switch (category) {
            case "제목":
                return educationRepository.findByPolicyNameContaining(keyword, pageable)
                        .map(entity -> modelMapper.map(entity, EducationDTO.class));
            case "내용":
                return educationRepository.findByPolicyOverviewContaining(keyword, pageable)
                        .map(entity -> modelMapper.map(entity, EducationDTO.class));
            default: // "전체"
                return educationRepository.findByPolicyNameContainingOrPolicyOverviewContaining(keyword, keyword, pageable)
                        .map(entity -> modelMapper.map(entity, EducationDTO.class));
        }
    }

    // 모든 정책 가져오기
    public List<EducationDTO> getAllPolicies() {
        List<EducationDTO> policies = educationRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, EducationDTO.class))
                .collect(Collectors.toList());
        System.out.println("Retrieved " + policies.size() + " policies");
        return policies;
    }

    // 특정 ID의 정책 가져오기
    public EducationDTO getPolicyById(Integer id) {
        return educationRepository.findById(id)
                .map(entity -> modelMapper.map(entity, EducationDTO.class))
                .orElseThrow(() -> new RuntimeException("Policy not found for ID: " + id));
    }
}
