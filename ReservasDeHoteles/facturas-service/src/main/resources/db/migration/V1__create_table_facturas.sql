CREATE TABLE facturas(
    id                      BIGINT      PRIMARY KEY AUTO_INCREMENT,
    folio                   VARCHAR(5)   NOT NULL    UNIQUE,
    id_reserva              BIGINT      NOT NULL    UNIQUE,
    id_pago                 BIGINT      NOT NULL    UNIQUE,
    run_huesped             VARCHAR(10)   NOT NULL,
    nombre_huesped          VARCHAR(150)   NOT NULL,
    fecha_check_in          DATETIME    NOT NULL,
    fecha_check_out         DATETIME    NULL,
    descripcion_habitacion  VARCHAR(50)   NOT NULL,
    cantidad_dias           INT     NOT NULL,
    subtotal                INT     NOT NULL,
    impuestos               INT     NOT NULL,
    total                   INT     NOT NULL,
    estado                  VARCHAR(20)   NOT NULL,
    fecha_factura           DATETIME    NOT NULL
);