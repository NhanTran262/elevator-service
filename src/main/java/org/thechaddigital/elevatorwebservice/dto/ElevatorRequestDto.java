package org.thechaddigital.elevatorwebservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.thechaddigital.elevatorwebservice.constant.RequestType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElevatorRequestDto {
    Long elevatorId;
    Integer targetFloor;
    Boolean direction;
    Boolean status;
    RequestType type;
}
