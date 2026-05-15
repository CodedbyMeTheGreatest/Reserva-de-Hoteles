CREATE TABLE check_in(
    id              BIGINT          PRIMARY KEY AUTO_INCREMENT,
    fecha_ingreso   DATETIME        NOT NULL,
    id_reserva      BIGINT          NOT NULL UNIQUE,
    id_empleado     BIGINT          NOT NULL,
    observaciones   VARCHAR(250)    NULL
)