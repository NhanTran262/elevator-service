package org.thechaddigital.elevatorwebservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.thechaddigital.elevatorwebservice.constant.RequestType;

@Entity
@Table(name = "elevator_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElevatorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JoinColumn(name="target_floor")
    Integer targetFloor;
    Boolean direction;
    Boolean status;
    @Enumerated(EnumType.STRING)
    RequestType type;
    @OneToOne()
    Elevator elevator;

    @Override
    public String toString() {
        return "ElevatorRequest{" +
                "id=" + id +
                ", floor=" + targetFloor +
                ", direction=" + direction +
                ", status=" + status +
                ", type=" + type +
                ", elevator=" + elevator +
                '}';
    }
}
