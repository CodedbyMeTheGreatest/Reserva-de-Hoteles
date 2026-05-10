CREATE TABLE checkins(
    id              BIGINT  PRIMARY KEY AUTO_GENERATED,
    fecha_ingreso   DATE    NOT NULL,
    id_reserva      BIGINT  NOT NULL,
    id_empleado     BIGINT  NOT NULL,
    observaciones   VARCHAR NULL
)