package project.blobus.Backend.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import project.blobus.Backend.community.dto.PostListDTO;
import project.blobus.Backend.community.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // boardType 필터만 사용하는 간단한 메소드
    List<Post> findByBoardType(String boardType);

    // Pageable + Specification을 통한 동적 쿼리 처리
    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    // PostListDTO를 반환하는 쿼리 정의
    @Query("SELECT new project.blobus.Backend.community.dto.PostListDTO(p.id, p.title, p.createdAt, p.authorId) " +
            "FROM Post p " +
            "WHERE (:boardType IS NULL OR p.boardType = :boardType) " +
            "AND (:userType IS NULL OR p.userType = :userType)")
    Page<PostListDTO> findPostListDtos(Post.BoardType boardType, Post.UserType userType, Pageable pageable);



}
