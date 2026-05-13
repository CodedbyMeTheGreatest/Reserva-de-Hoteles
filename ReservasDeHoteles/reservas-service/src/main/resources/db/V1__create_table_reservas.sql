CREATE TABLE reserva (
    id_reserva BIGINT PRIMARY KEY NOT NULL,
    id_habitacion BIGINT NOT NULL,
    id_huesped BIGINT NOT NULL,
    id_empleado BIGINT NOT NULL,
    cant_dias INT NOT NULL,
    id_checkin BIGINT NOT NULL,
    id_checkout BIGINT,
    FOREIGN KEY (id_habitacion) REFERENCES habitacion(id_habitacion),
    FOREIGN KEY (id_huesped) REFERENCES huesped(id_huesped),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
    FOREIGN KEY (id_checkin) REFERENCES check_in(id_checkin),
    FOREIGN KEY (id_checkout) REFERENCES check_out(id_checkout)
);
