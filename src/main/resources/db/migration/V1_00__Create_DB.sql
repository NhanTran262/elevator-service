CREATE TABLE IF NOT EXISTS `elevators`
(
    `id`            BIGINT AUTO_INCREMENT,
    `name`          VARCHAR(10),
    `current_floor` INT    DEFAULT 1,
    `is_moving_up`  BIT(1) DEFAULT 1,
    `is_door_open`  BIT(1) DEFAULT 0,
    CONSTRAINT elevator_pk PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `floors`
(
    `id`    BIGINT AUTO_INCREMENT,
    `floor_number` VARCHAR(10),
    CONSTRAINT floor_pk PRIMARY KEY (`id`)
);
CREATE TABLE IF NOT EXISTS `elevator_requests`
(
    `id`           BIGINT AUTO_INCREMENT,
    `elevator_id`  BIGINT,
    `target_floor` INT,
    `direction`    BIT(1) DEFAULT 1,
    `type`         ENUM ('CALL','OPEN_DOOR', 'CLOSE_DOOR','SELECT'),
    `status`       BIT(1) DEFAULT 1,
    CONSTRAINT elevator_request_pk PRIMARY KEY (`id`),
    CONSTRAINT elevator_request_elevator_fk FOREIGN KEY (`elevator_id`) REFERENCES elevators (`id`)
);
INSERT INTO `elevators` (`name`)
VALUES ('Elevator 1'),
       ('Elevator 2'),
       ('Elevator 3');
INSERT INTO `floors` (`floor_number`)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10);
