package project.blobus.Backend.youth.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.blobus.Backend.youth.house.entity.HouseEntity;

public interface HouseRepository extends JpaRepository<HouseEntity, Long> {
}
