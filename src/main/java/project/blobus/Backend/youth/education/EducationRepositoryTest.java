package project.blobus.Backend.youth.education;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepositoryTest extends JpaRepository<EducationEntityTest, Integer> {
    // 필요한 경우 추가 메서드 정의 가능
    Page<EducationEntityTest> findAll(Pageable pageable);

    // 제목(title)에서 검색
    Page<EducationEntityTest> findByTitleContaining(String keyword, Pageable pageable);

    // 내용(overview)에서 검색
    Page<EducationEntityTest> findByOverviewContaining(String keyword, Pageable pageable);

    // 제목 또는 내용에서 검색
    Page<EducationEntityTest> findByTitleContainingOrOverviewContaining(String titleKeyword, String overviewKeyword, Pageable pageable);
}
