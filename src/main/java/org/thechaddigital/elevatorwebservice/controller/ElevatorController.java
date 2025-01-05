package org.thechaddigital.elevatorwebservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thechaddigital.elevatorwebservice.dto.ElevatorDto;
import org.thechaddigital.elevatorwebservice.dto.ElevatorRequestDto;
import org.thechaddigital.elevatorwebservice.payload.response.ElevatorResponse;
import org.thechaddigital.elevatorwebservice.service.ElevatorService;

import java.util.List;

@RestController
@RequestMapping("api/elevators")
@AllArgsConstructor
@CrossOrigin("*")
public class ElevatorController {
    private final ElevatorService elevatorService;

    @GetMapping
    public ResponseEntity<List<ElevatorDto>> getElevators() {
        List<ElevatorDto> elevatorDtoList = elevatorService.getAllElevators();
        return ResponseEntity.ok(elevatorDtoList);
    }

    @PostMapping("/call")
    public ResponseEntity<ElevatorResponse> callElevator(@RequestBody ElevatorRequestDto elevatorRequestDto) {
        ElevatorResponse elevatorResponse = elevatorService.callElevator(elevatorRequestDto);
        return ResponseEntity.ok(elevatorResponse);
    }
    @PostMapping("/select")
    public ResponseEntity<ElevatorResponse> selectFloor(@RequestBody ElevatorRequestDto elevatorRequestDto) {
        ElevatorResponse elevatorResponse = elevatorService.selectFloor(elevatorRequestDto);
        return ResponseEntity.ok(elevatorResponse);
    }
    

}
