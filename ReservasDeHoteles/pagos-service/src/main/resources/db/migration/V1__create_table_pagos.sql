CREATE TABLE pagos (
    id_pago BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_habitacion BIGINT NOT NULL,
    id_huesped BIGINT NOT NULL,
    precio_por_noche DECIMAL(10, 2) NOT NULL,
    cant_dias INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    impuestos DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    metodo_pago VARCHAR(20) NOT NULL CHECK (metodo_pago IN ('TARJETA', 'EFECTIVO', 'TRANSFERENCIA')),
    estado_pago VARCHAR(20) NOT NULL CHECK (estado_pago IN ('PENDIENTE', 'PAGADO', 'RECHAZADO')),
    fecha_pago TIMESTAMP

);
