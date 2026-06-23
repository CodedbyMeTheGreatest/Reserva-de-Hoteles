CREATE TABLE check_out(
    id              BIGINT          PRIMARY KEY AUTO_INCREMENT,
    fecha_salida    TIMESTAMP        NOT NULL,
    id_reserva      BIGINT          NOT NULL    UNIQUE,
    id_empleado     BIGINT          NOT NULL,
    observaciones   VARCHAR(250)    NULL
);