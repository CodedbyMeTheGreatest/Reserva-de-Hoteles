CREATE TABLE facturas(
    id                      BIGINT      PRIMARY KEY AUTO_INCREMENT,
    folio                   VARCHAR()   NOT NULL    UNIQUE,
    id_reserva              BIGINT      NOT NULL    UNIQUE,
    id_pago                 BIGINT      NOT NULL    UNIQUE,
    run_huesped             VARCHAR()   NOT NULL,
    nombre_huesped          VARCHAR()   NOT NULL,
    fecha_check_in          DATETIME    NOT NULL,
    fecha_check_out         DATETIME    NULL,
    descripcion_habitacion  VARCHAR()   NOT NULL,
    cantidad_dias           NUMERIC     NOT NULL,
    subtotal                NUMERIC     NOT NULL,
    impuestos               NUMERIC     NOT NULL,
    total                   NUMERIC     NOT NULL,
    estado                  VARCHAR()   NOT NULL,
    fecha_factura           DATETIME    NOT NULL
);