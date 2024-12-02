package project.blobus.Backend.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.community.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
