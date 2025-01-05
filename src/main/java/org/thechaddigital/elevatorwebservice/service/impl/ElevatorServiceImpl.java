package org.thechaddigital.elevatorwebservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thechaddigital.elevatorwebservice.dto.ElevatorDto;
import org.thechaddigital.elevatorwebservice.entity.Elevator;
import org.thechaddigital.elevatorwebservice.payload.request.ElevatorRequest;
import org.thechaddigital.elevatorwebservice.payload.response.ElevatorResponse;
import org.thechaddigital.elevatorwebservice.repository.ElevatorRepository;
import org.thechaddigital.elevatorwebservice.service.ElevatorService;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElevatorServiceImpl implements ElevatorService {
    private final ElevatorRepository elevatorRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ElevatorServiceImpl.class);

    @Override
    public List<ElevatorDto> getAllElevators() {
        LOGGER.info("ElevatorServiceImpl -> Get All Elevators Invoked.");
        try {
            List<Elevator> elevators = elevatorRepository.findAll();
            return elevators.stream()
                    .map(elevator -> {
                        ElevatorDto elevatorDto = new ElevatorDto();
                        BeanUtils.copyProperties(elevator, elevatorDto);
                        return elevatorDto;
                    }).toList();
        } catch (Exception e) {
            LOGGER.error("ElevatorServiceImpl -> Get All Elevators Error: {}.", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public ElevatorResponse callElevator(ElevatorRequest elevatorRequest) {
        LOGGER.info("ElevatorServiceImpl -> Call Elevator Invoked.");
        try {
            List<Elevator> elevators = elevatorRepository.findAll();
            Elevator elevator = findNearestElevator(elevators, elevatorRequest);
            elevator.setCurrentFloor(elevatorRequest.getTargetFloor());
            elevator.setIsMovingUp(elevatorRequest.getDirection());
            elevatorRepository.save(elevator);
            return ElevatorResponse.builder()
                    .id(elevator.getId())
                    .currentFloor(elevator.getCurrentFloor())
                    .targetFloor(elevatorRequest.getTargetFloor())
                    .build();
        } catch (Exception e) {
            LOGGER.error("ElevatorServiceImpl -> Call Elevator Error: {}.", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public ElevatorResponse selectFloor(ElevatorRequest elevatorRequest) {
        LOGGER.info("ElevatorServiceImpl -> Select Floor Invoked.");
        try {
            Elevator elevator = elevatorRepository.findById(elevatorRequest.getElevatorId())
                    .orElseThrow(() -> new IllegalArgumentException("Elevator not found"));
            elevator.setCurrentFloor(elevatorRequest.getTargetFloor());
            elevator.setIsMovingUp(elevatorRequest.getDirection());
            elevatorRepository.save(elevator);
            return ElevatorResponse.builder()
                    .id(elevator.getId())
                    .currentFloor(elevator.getCurrentFloor())
                    .targetFloor(elevatorRequest.getTargetFloor())
                    .build();
        } catch (Exception e) {
            LOGGER.error("ElevatorServiceImpl -> Select Floor Error: {}.", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private Elevator findNearestElevator(List<Elevator> elevators, ElevatorRequest elevatorRequest) {
        return elevators.stream()
                .filter(elevator -> (elevator.getIsMovingUp() && elevator.getCurrentFloor() >= elevatorRequest.getTargetFloor())
                        || (!elevator.getIsMovingUp() && elevator.getCurrentFloor() <= elevatorRequest.getTargetFloor())
                        || !elevator.getIsMovingUp())
                .min(Comparator.comparingInt(elevator -> Math.abs(elevator.getCurrentFloor() - elevatorRequest.getTargetFloor())))
                .orElse(null);
    }

}
