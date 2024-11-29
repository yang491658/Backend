package project.blobus.Backend.youth.house.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.youth.house.entity.HouseEntity;

public interface HouseRepository extends JpaRepository<HouseEntity, Long> {

    // 제목 검색
    Page<HouseEntity> findByPolyBizSjnmContaining(String searchTerm, Pageable pageable);

    // 내용 검색
    Page<HouseEntity> findByPolyItcnCnContaining(String searchTerm, Pageable pageable);

    // 제목 + 내용 검색
    Page<HouseEntity> findByPolyBizSjnmContainingOrPolyItcnCnContaining(String searchTerm1, String searchTerm2, Pageable pageable);

}
