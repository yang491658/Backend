package project.blobus.Backend.temp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.temp.entity.TempResourceSupport;

public interface ResourceSupportRepository extends JpaRepository<TempResourceSupport, Long> {
}
