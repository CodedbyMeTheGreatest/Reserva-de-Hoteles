CREATE TABLE disponibilidades (
    id_disponibilidad BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    estado VARCHAR(20) NOT NULL CHECK (estado IN ('DISPONIBLE', 'OCUPADA', 'MANTENIMIENTO')),
    fecha_desde TIMESTAMP NOT NULL,
    fecha_hasta TIMESTAMP
);
