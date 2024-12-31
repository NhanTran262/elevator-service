package org.thechaddigital.elevatorwebservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thechaddigital.elevatorwebservice.dto.ElevatorDto;
import org.thechaddigital.elevatorwebservice.service.ElevatorService;

import java.util.List;

@RestController
@RequestMapping("api/elevators")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ElevatorController {
    private final ElevatorService elevatorService;

    @GetMapping
    public ResponseEntity<List<ElevatorDto>> getElevators() {
        List<ElevatorDto> elevatorDtoList = elevatorService.getAllElevators();
        return ResponseEntity.ok().body(elevatorDtoList);
    }
}
