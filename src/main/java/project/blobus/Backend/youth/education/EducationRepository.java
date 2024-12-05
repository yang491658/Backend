package project.blobus.Backend.youth.education;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<EducationEntity, Integer> {
    EducationEntity findByBizId(String bizId);

    // 필요한 경우 추가 메서드 정의 가능
    Page<EducationEntity> findAll(Pageable pageable);

    // 제목(title)에서 검색
    Page<EducationEntity> findByPolicyNameContaining(String keyword, Pageable pageable);

    // 내용(overview)에서 검색
    Page<EducationEntity> findByPolicyOverviewContaining(String keyword, Pageable pageable);

    // 제목 또는 내용에서 검색
    Page<EducationEntity> findByPolicyNameContainingOrPolicyOverviewContaining(String titleKeyword, String overviewKeyword, Pageable pageable);
}
