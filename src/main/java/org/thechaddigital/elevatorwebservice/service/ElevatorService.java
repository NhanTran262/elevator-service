package org.thechaddigital.elevatorwebservice.service;

import org.thechaddigital.elevatorwebservice.dto.ElevatorDto;
import org.thechaddigital.elevatorwebservice.dto.ElevatorRequestDto;
import org.thechaddigital.elevatorwebservice.payload.response.ElevatorResponse;

import java.util.List;

public interface ElevatorService {
    List<ElevatorDto> getAllElevators();

    ElevatorResponse callElevator(ElevatorRequestDto elevatorRequestDto);

    ElevatorResponse selectFloor(ElevatorRequestDto elevatorRequestDto);

}
