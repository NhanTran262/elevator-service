package org.thechaddigital.elevatorwebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thechaddigital.elevatorwebservice.entity.ElevatorRequest;

import java.util.List;

public interface ElevatorRequestRepository extends JpaRepository<ElevatorRequest, Long> {
    List<ElevatorRequest> findByElevatorIsNull();
}
