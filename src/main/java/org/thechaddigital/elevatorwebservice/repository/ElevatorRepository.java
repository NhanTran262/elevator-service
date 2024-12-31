package org.thechaddigital.elevatorwebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thechaddigital.elevatorwebservice.entity.Elevator;

public interface ElevatorRepository extends JpaRepository<Elevator, Long> {
}
