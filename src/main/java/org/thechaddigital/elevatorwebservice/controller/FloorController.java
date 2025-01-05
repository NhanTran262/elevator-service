package org.thechaddigital.elevatorwebservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thechaddigital.elevatorwebservice.dto.FloorDto;
import org.thechaddigital.elevatorwebservice.service.FloorService;

import java.util.List;

@RestController
@RequestMapping("api/floors")
@AllArgsConstructor
@CrossOrigin("*")
public class FloorController {
    private final FloorService floorService;

    @GetMapping
    public ResponseEntity<List<FloorDto>> getAllFloors() {
        List<FloorDto> floorDtoList = floorService.getAllFloors();
        return ResponseEntity.ok(floorDtoList);
    }
}
