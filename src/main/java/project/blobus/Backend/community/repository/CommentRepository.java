package project.blobus.Backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.community.entity.CommuntiyComment;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommuntiyComment, Long> {
    List<CommuntiyComment> findAllByPost_Id(Long postId);
}
