CREATE TABLE empleados(
    id              BIGINT          PRIMARY KEY AUTO_INCREMENT,
    run             VARCHAR(12)     NOT NULL UNIQUE,
    nombre_completo VARCHAR(100)    NOT NULL,
    cargo           VARCHAR(50)     NULL,
    id_hotel        BIGINT          NOT NULL
)
