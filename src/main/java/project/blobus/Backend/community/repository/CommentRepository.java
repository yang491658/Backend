package project.blobus.Backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.community.entity.CommunityComment;

public interface CommentRepository extends JpaRepository<CommunityComment, Integer> {
}
