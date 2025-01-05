package org.thechaddigital.elevatorwebservice.mapper.impl;

import org.springframework.stereotype.Component;
import org.thechaddigital.elevatorwebservice.dto.ElevatorRequestDto;
import org.thechaddigital.elevatorwebservice.entity.Elevator;
import org.thechaddigital.elevatorwebservice.entity.ElevatorRequest;
import org.thechaddigital.elevatorwebservice.mapper.ElevatorRequestMapper;

@Component
public class ElevatorRequestMapperImpl implements ElevatorRequestMapper {

    @Override
    public ElevatorRequest dtoToEntity(Elevator elevator, ElevatorRequestDto elevatorRequestDto) {
        return ElevatorRequest.builder()
                .elevator(elevator)
                .direction(elevator.getIsMovingUp())
                .targetFloor(elevatorRequestDto.getTargetFloor())
                .type(elevatorRequestDto.getType())
                .status(false)
                .build();
    }
}
