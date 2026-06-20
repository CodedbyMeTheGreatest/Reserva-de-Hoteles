--Script que se ejecuta al iniciar por primera vez
--Crea las bases de datos de cada microservicio
CREATE DATABASE IF NOT EXISTS db_hoteleria_hoteles 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_habitaciones 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_pagos 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_reservas 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_disponibilidades 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_huespedes 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_empleados 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_facturas 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_check_in 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS db_hoteleria_check_out 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

--Otorga permisos
GRANT ALL PRIVILEGES ON db_hoteleria_hoteles.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_habitaciones.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_pagos.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_reservas.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_disponibilidades.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_huespedes.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_empleados.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_facturas.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_check_in.* TO 'spring'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_check_out.* TO 'spring'@'%';