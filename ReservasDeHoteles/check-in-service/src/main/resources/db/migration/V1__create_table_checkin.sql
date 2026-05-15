CREATE TABLE check_in(
    id              BIGINT          PRIMARY KEY AUTO_INCREMENT,
    id_reserva      BIGINT          NOT NULL UNIQUE,
    id_empleado     BIGINT          NOT NULL,
    fecha_ingreso   DATETIME        NOT NULL,
    observaciones   VARCHAR()    NULL
)