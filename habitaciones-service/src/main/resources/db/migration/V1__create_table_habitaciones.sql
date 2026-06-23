CREATE TABLE habitaciones (
    id_habitacion BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    numero VARCHAR(10) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    precio_por_noche INT NOT NULL,
    id_hotel BIGINT NOT NULL,
    id_disponibilidad BIGINT NOT NULL
);
