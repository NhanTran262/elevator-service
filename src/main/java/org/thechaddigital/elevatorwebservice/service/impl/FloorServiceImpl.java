package org.thechaddigital.elevatorwebservice.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thechaddigital.elevatorwebservice.dto.FloorDto;
import org.thechaddigital.elevatorwebservice.entity.Floor;
import org.thechaddigital.elevatorwebservice.repository.FloorRepository;
import org.thechaddigital.elevatorwebservice.service.FloorService;

import java.util.List;

@Service
@AllArgsConstructor
public class FloorServiceImpl implements FloorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FloorServiceImpl.class);
    private final FloorRepository floorRepository;

    @Override
    public List<FloorDto> getAllFloors() {
        LOGGER.info("FloorServiceImpl -> Get All Floors Invoked");
        try {
            List<Floor> floors = floorRepository.findAll();
            return floors.stream()
                    .map(floor -> {
                        FloorDto floorDto = new FloorDto();
                        BeanUtils.copyProperties(floor, floorDto);
                        return floorDto;
                    }).toList();
        }catch (Exception e) {
            LOGGER.error("FloorServiceImpl -> Get All Floors Error: {}.",e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
