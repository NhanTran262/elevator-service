package org.thechaddigital.elevatorwebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thechaddigital.elevatorwebservice.entity.Floor;

public interface FloorRepository extends JpaRepository<Floor, Long> {
}
