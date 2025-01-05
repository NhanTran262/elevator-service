package org.thechaddigital.elevatorwebservice.mapper;

import org.thechaddigital.elevatorwebservice.dto.ElevatorRequestDto;
import org.thechaddigital.elevatorwebservice.entity.Elevator;
import org.thechaddigital.elevatorwebservice.entity.ElevatorRequest;

public interface ElevatorRequestMapper {
    ElevatorRequest dtoToEntity(Elevator elevator, ElevatorRequestDto elevatorRequestDto);
}
