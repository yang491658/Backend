package project.blobus.Backend.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.resource.entity.ResourceCulture;

public interface ResourceCultureRepository extends JpaRepository<ResourceCulture, Long> {
    boolean existsByResNum(Long resNum);
}
