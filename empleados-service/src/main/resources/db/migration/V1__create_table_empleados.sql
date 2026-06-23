CREATE TABLE empleados(
    id              BIGINT      PRIMARY KEY AUTO_INCREMENT,
    run             VARCHAR(10)   NOT NULL    UNIQUE,
    nombre_completo VARCHAR(150)   NOT NULL,
    cargo           VARCHAR(20)   NULL,
    id_hotel        BIGINT      NOT NULL,
    nombre_hotel    VARCHAR(50)   NULL
)
