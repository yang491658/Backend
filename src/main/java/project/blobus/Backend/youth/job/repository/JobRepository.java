package project.blobus.Backend.youth.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.blobus.Backend.youth.job.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
    // 정책현황 - 정책번호 찾기
    boolean existsByBizId(String bizId);

    // 정책현황 - 리스트
    @Query("SELECT j " +
            "FROM JobEntity j " +
            "WHERE j.delFlag = false " +
            "AND ( " +
            "(:filterType = 'both' AND (j.polyBizSjnm LIKE %:searchTerm% OR j.polyItcnCn LIKE %:searchTerm%)) " +
            "OR (:filterType = 'polyBizSjnm' AND j.polyBizSjnm LIKE %:searchTerm%) " +
            "OR (:filterType = 'polyItcnCn' AND j.polyItcnCn LIKE %:searchTerm%) " +
            ") " +
            "AND ( " +
            "(:policyStsType = 'stsAll') OR " +
            "(:policyStsType = 'stsOngoing' AND (j.rqutPrdEnd >= CURRENT_DATE OR j.rqutPrdEnd IS NULL)) OR " +
            "(:policyStsType = 'stsClosed' AND j.rqutPrdEnd < CURRENT_DATE) " +
            ")")
    Page<JobEntity> findByFilterTypeAndPolicyStsType(String policyStsType,
                                                     String searchTerm,
                                                     String filterType,
                                                     Pageable pageable);

    // 정책현황 - 수정
    @Modifying
    @Query("UPDATE JobEntity j SET j.delFlag = TRUE WHERE j.id = :policyId")
    void delFlagById(@Param("policyId") Long policyId);

}
