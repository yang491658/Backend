package project.blobus.Backend.temp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.temp.entity.TempCommunityFreeBoard;

public interface CommunityFreeBoardRepository extends JpaRepository<TempCommunityFreeBoard, Long> {
}
