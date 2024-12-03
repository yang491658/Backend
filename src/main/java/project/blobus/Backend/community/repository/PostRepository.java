package project.blobus.Backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.community.entity.CommunityPost;

public interface PostRepository extends JpaRepository<CommunityPost, Long> {
}
