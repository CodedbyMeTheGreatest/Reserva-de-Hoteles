CREATE TABLE reservas (
    id_reserva BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_habitacion BIGINT NOT NULL,
    id_huesped BIGINT NOT NULL,
    id_empleado BIGINT NOT NULL,
    cant_dias INT NOT NULL,
    id_checkin BIGINT NOT NULL,
    id_checkout BIGINT
);
