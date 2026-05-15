CREATE TABLE empleados(
    id              BIGINT      PRIMARY KEY AUTO_INCREMENT,
    run             VARCHAR()   NOT NULL    UNIQUE,
    nombre_completo VARCHAR()   NOT NULL,
    cargo           VARCHAR()   NULL,
    id_hotel        BIGINT      NOT NULL,
    nombre_hotel    VARCHAR()   NULL
)
