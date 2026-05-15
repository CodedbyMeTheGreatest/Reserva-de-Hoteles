CREATE TABLE habitacion (
    id_habitacion BIGINT PRIMARY KEY NOT NULL,
    nro VARCHAR(20) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    precio_por_noche DECIMAL(10,2) NOT NULL,
    id_hotel BIGINT NOT NULL,
    id_disponibilidad BIGINT NOT NULL,
    FOREIGN KEY (id_hotel) REFERENCES hotel(id_hotel),
    FOREIGN KEY (id_disponibilidad) REFERENCES disponibilidad(id_disponibilidad)

);
