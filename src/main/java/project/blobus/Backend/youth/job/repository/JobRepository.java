package project.blobus.Backend.youth.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.youth.job.entity.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
    boolean existsByBizId(String bizId);

    // 제목 검색
    Page<JobEntity> findByPolyBizSjnmContaining(String searchTerm, Pageable pageable);

    // 내용 검색
    Page<JobEntity> findByPolyItcnCnContaining(String searchTerm, Pageable pageable);

    // 제목 + 내용 검색
    Page<JobEntity> findByPolyBizSjnmContainingOrPolyItcnCnContaining(String searchTerm1, String searchTerm2, Pageable pageable);

}
