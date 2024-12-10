package project.blobus.Backend.youth.education;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationService {

    private final EducationRepository educationRepository;
    private final ModelMapper modelMapper;

    // 페이징된 정책 목록 가져오기
    public Page<EducationDTO> getPagedPolicies(String keyword, String progress, String category, Pageable pageable) {
        LocalDate currentDate = LocalDate.now();
        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";
        }
        if (category == null || category.trim().isEmpty()) {
            category = "유형전체";
        }
        if (progress == null || progress.trim().isEmpty()) {
            progress = "상태전체";
        }
        return educationRepository.findByCategoryAndProgress(keyword, category, progress, currentDate, pageable)
                .map(entity -> modelMapper.map(entity, EducationDTO.class));
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

    // 특정 ID의 정책 게시글 수정(관리자)
    public void modify(EducationDTO educationDTO) {
        Optional<EducationEntity> result = educationRepository.findById(educationDTO.getPolicyId());

        EducationEntity educationEntity = result.orElseThrow();

        modelMapper.map(educationDTO, educationEntity);

        educationRepository.save(educationEntity);
    }

    @Transactional
    public void remove(Integer id) {
        educationRepository.updateToDelete(id, true);
    }
}
