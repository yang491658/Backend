package project.blobus.Backend.youth.finance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepositoryTest extends JpaRepository<FinanceEntityTest, Integer> {
    // 필요한 경우 추가 메서드 정의 가능
    Page<FinanceEntityTest> findAll(Pageable pageable);
}
