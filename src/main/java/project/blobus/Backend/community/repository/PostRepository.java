package project.blobus.Backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.community.entity.CommunityPost;

import java.util.Optional;

public interface PostRepository extends JpaRepository<CommunityPost, Long> {
    Optional<CommunityPost> findFirstByIdLessThanOrderByIdDesc(Long id);

    Optional<CommunityPost> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
