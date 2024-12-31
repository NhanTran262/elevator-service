package org.thechaddigital.elevatorwebservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElevatorDto {
    Long id;
    String name;
    Integer currentFloor;
    Boolean isMovingUp;
    Boolean isDoorOpen;
}
