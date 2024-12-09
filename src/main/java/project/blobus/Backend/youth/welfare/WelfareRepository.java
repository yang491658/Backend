package project.blobus.Backend.youth.welfare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WelfareRepository extends JpaRepository<WelfareEntity, Integer> {

    WelfareEntity findByBizId(String bizId);

    // 필요한 경우 추가 메서드 정의 가능
    @Query("SELECT w FROM WelfareEntity w WHERE w.delFlag = false")
    Page<WelfareEntity> findAll(Pageable pageable);

    // 제목(title)에서 검색
    Page<WelfareEntity> findByPolicyNameContaining(String keyword, Pageable pageable);

    // 내용(overview)에서 검색
    Page<WelfareEntity> findByPolicyOverviewContaining(String keyword, Pageable pageable);

    // 제목 또는 내용에서 검색
    Page<WelfareEntity> findByPolicyNameContainingOrPolicyOverviewContaining(String titleKeyword, String overviewKeyword, Pageable pageable);

    @Modifying
    @Query("UPDATE WelfareEntity w SET w.delFlag = :flag WHERE w.policyId = :id")
    void updateToDelete(@Param("id") Integer id, @Param("flag") boolean flag);
}
