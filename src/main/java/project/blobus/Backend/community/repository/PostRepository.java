package project.blobus.Backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.community.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoardType(String boardType);
}
