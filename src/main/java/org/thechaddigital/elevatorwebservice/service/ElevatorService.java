package org.thechaddigital.elevatorwebservice.service;

import org.thechaddigital.elevatorwebservice.dto.ElevatorDto;
import org.thechaddigital.elevatorwebservice.dto.ElevatorRequestDto;

import java.util.List;

public interface ElevatorService {
    List<ElevatorDto> getAllElevators();

    void callElevator(ElevatorRequestDto elevatorRequestDto);

    void selectFloor(Long elevatorId, Integer floor);

    void openElevator(Long elevatorId);

    void closeElevator(Long elevatorId);
}
