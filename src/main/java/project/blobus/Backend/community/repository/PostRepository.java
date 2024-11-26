package project.blobus.Backend.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import project.blobus.Backend.community.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // boardType 필터만 사용하는 간단한 메소드
    List<Post> findByBoardType(String boardType);

    // Pageable + Specification을 통한 동적 쿼리 처리
    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    // @Query 사용 (Optional): 필터링 로직을 직접 SQL로 작성한 메소드
    @Query("SELECT p FROM Post p WHERE " +
            "(:boardType IS NULL OR p.boardType = :boardType) AND " +
            "(:userType IS NULL OR p.userType = :userType) AND " +
            "(:searchTerm IS NULL OR p.title LIKE %:searchTerm% OR p.content LIKE %:searchTerm%)")
    Page<Post> findByFilters(@Param("boardType") String boardType,
                             @Param("userType") String userType,
                             @Param("searchTerm") String searchTerm,
                             Pageable pageable);
}
