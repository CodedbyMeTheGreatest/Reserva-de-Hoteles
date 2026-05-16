CREATE TABLE habitacion (
    id_habitacion BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    numero VARCHAR(10) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    precio_por_noche DECIMAL(10,2) NOT NULL,
    id_hotel BIGINT NOT NULL,
    id_disponibilidad BIGINT NOT NULL,
);
