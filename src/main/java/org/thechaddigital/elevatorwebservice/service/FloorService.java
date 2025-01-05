package org.thechaddigital.elevatorwebservice.service;

import org.thechaddigital.elevatorwebservice.dto.FloorDto;

import java.util.List;

public interface FloorService {
    List<FloorDto> getAllFloors();
}
