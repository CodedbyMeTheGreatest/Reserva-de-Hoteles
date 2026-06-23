-- init-db.sql
-- Crear usuario si no existe
CREATE USER IF NOT EXISTS 'hotel_user'@'%' IDENTIFIED BY 'hotel123';

-- Crear todas las bases de datos
CREATE DATABASE IF NOT EXISTS db_hoteleria_hoteles;
CREATE DATABASE IF NOT EXISTS db_hoteleria_disponibilidades;
CREATE DATABASE IF NOT EXISTS db_hoteleria_habitaciones;
CREATE DATABASE IF NOT EXISTS db_hoteleria_empleados;
CREATE DATABASE IF NOT EXISTS db_hoteleria_huespedes;
CREATE DATABASE IF NOT EXISTS db_hoteleria_reservas;
CREATE DATABASE IF NOT EXISTS db_hoteleria_check_in;
CREATE DATABASE IF NOT EXISTS db_hoteleria_check_out;
CREATE DATABASE IF NOT EXISTS db_hoteleria_pagos;
CREATE DATABASE IF NOT EXISTS db_hoteleria_facturas;

-- Otorgar permisos al usuario en TODAS las bases de datos
GRANT ALL PRIVILEGES ON db_hoteleria_hoteles.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_disponibilidades.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_habitaciones.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_empleados.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_huespedes.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_reservas.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_check_in.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_check_out.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_pagos.* TO 'hotel_user'@'%';
GRANT ALL PRIVILEGES ON db_hoteleria_facturas.* TO 'hotel_user'@'%';

-- Aplicar cambios
FLUSH PRIVILEGES;