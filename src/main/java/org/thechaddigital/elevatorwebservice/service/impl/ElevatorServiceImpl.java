package org.thechaddigital.elevatorwebservice.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.thechaddigital.elevatorwebservice.dto.ElevatorDto;
import org.thechaddigital.elevatorwebservice.dto.ElevatorRequestDto;
import org.thechaddigital.elevatorwebservice.entity.Elevator;
import org.thechaddigital.elevatorwebservice.entity.ElevatorRequest;
import org.thechaddigital.elevatorwebservice.repository.ElevatorRepository;
import org.thechaddigital.elevatorwebservice.repository.ElevatorRequestRepository;
import org.thechaddigital.elevatorwebservice.service.ElevatorService;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ElevatorServiceImpl implements ElevatorService {
    private final ElevatorRepository elevatorRepository;
    private final ElevatorRequestRepository elevatorRequestRepository;
    private final TransactionTemplate transactionTemplate;
    private final BlockingDeque<ElevatorRequest> requestQueue = new LinkedBlockingDeque<>();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private static final Logger LOGGER = LoggerFactory.getLogger(ElevatorServiceImpl.class);

    @PostConstruct
    public void init() {
        processQueue();
    }

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
    @Transactional
    public void callElevator(ElevatorRequestDto elevatorRequestDto) {
        ElevatorRequest elevatorRequest = new ElevatorRequest();
        BeanUtils.copyProperties(elevatorRequestDto, elevatorRequest);
        elevatorRequestRepository.save(elevatorRequest);
        requestQueue.offer(elevatorRequest);
    }

    @Override
    @Transactional
    public void selectFloor(Long elevatorId, Integer floor) {
        Elevator elevator = elevatorRepository.findById(elevatorId)
                .orElseThrow(() -> new IllegalArgumentException("Elevator not found"));
        ElevatorRequest elevatorRequest = new ElevatorRequest();
        elevatorRequest.setElevator(elevator);
        elevatorRequest.setFloor(floor);
        elevatorRequest.setDirection(elevator.getIsMovingUp());
        elevatorRequestRepository.save(elevatorRequest);
        moveElevator(elevator);

    }

    @Override
    public void openElevator(Long elevatorId) {
        Elevator elevator = elevatorRepository.findById(elevatorId)
                .orElseThrow(() -> new IllegalArgumentException("Elevator not found"));
        elevator.setIsDoorOpen(true);
        transactionTemplate.execute(status -> {
            elevatorRepository.save(elevator);
            return null;
        });
    }

    @Override
    public void closeElevator(Long elevatorId) {
        Elevator elevator = elevatorRepository.findById(elevatorId)
                .orElseThrow(() -> new IllegalArgumentException("Elevator not found"));
        elevator.setIsDoorOpen(false);
        transactionTemplate.execute(status -> {
            elevatorRepository.save(elevator);
            return null;
        });
    }

    private void processQueue() {
        new Thread(() -> {
            try {
                while (true) {
                    ElevatorRequest request = requestQueue.take();
                    List<Elevator> elevators = elevatorRepository.findAll();
                    Elevator nearestElevator = findNearestElevator(elevators, request);
                    request.setElevator(nearestElevator);
                    elevatorRequestRepository.save(request);
                    moveElevator(nearestElevator);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private Elevator findNearestElevator(List<Elevator> elevators, ElevatorRequest elevatorRequest) {
        return elevators.stream()
                .filter(elevator ->
                        (elevator.getIsMovingUp() && elevatorRequest.getFloor() >= elevator.getCurrentFloor())
                                || (!elevator.getIsMovingUp() && elevatorRequest.getFloor() <= elevator.getCurrentFloor())
                                || !elevator.getIsMovingUp())
                .min(Comparator.comparingInt(elevator -> Math.abs(elevator.getCurrentFloor() - elevatorRequest.getFloor())))
                .orElse(null);
    }

    private void moveElevator(Elevator elevator) {
        List<ElevatorRequest> elevatorRequests = elevator.getElevatorRequests();
        if (elevatorRequests.isEmpty()) {
            throw new RuntimeException("No elevator requests found");
        }
        elevatorRequests.forEach(elevatorRequest -> {
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                synchronized (elevator) {
                    if (elevator.getCurrentFloor() < elevatorRequest.getFloor()) {
                        elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
                    } else {
                        elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
                    }
                    elevatorRepository.save(elevator);
                }
            }, 0, 5, TimeUnit.SECONDS);
            scheduledExecutorService.schedule(() -> openElevator(elevator.getId()), 2, TimeUnit.SECONDS);
            scheduledExecutorService.schedule(() -> closeElevator(elevator.getId()), 3, TimeUnit.SECONDS);

        });
        elevatorRequests.clear();
        elevatorRepository.save(elevator);
    }
}
