package project.blobus.Backend.youth.welfare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.blobus.Backend.youth.education.EducationEntity;

@Repository
public interface WelfareRepository extends JpaRepository<WelfareEntity, Integer> {
    WelfareEntity findByBizId(String bizId);

    // 필요한 경우 추가 메서드 정의 가능
    Page<WelfareEntity> findAll(Pageable pageable);

    // 제목(title)에서 검색
    Page<WelfareEntity> findByPolicyNameContaining(String keyword, Pageable pageable);

    // 내용(overview)에서 검색
    Page<WelfareEntity> findByPolicyOverviewContaining(String keyword, Pageable pageable);

    // 제목 또는 내용에서 검색
    Page<WelfareEntity> findByPolicyNameContainingOrPolicyOverviewContaining(String titleKeyword, String overviewKeyword, Pageable pageable);
}
