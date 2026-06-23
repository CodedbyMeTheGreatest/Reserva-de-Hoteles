CREATE TABLE huespedes(
    id              BIGINT      PRIMARY KEY AUTO_INCREMENT,
    run             VARCHAR(10)   NOT NULL    UNIQUE,
    nombre_completo VARCHAR(150)   NOT NULL,
    email           VARCHAR(150)   NOT NULL,
    telefono        VARCHAR(20)         NOT NULL,
    nacionalidad    VARCHAR(50)   NOT NULL
);