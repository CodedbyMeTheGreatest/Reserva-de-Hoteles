# Reserva-de-Hoteles - Sistema de Gestion de Reservas de Hoteles

Microservicios desarrollados en Spring Boot encargado de una gestion de reservas de hoteles.

### Microservicios y sus Puertos

    API-Gateway     -> 8080
    Hoteles         -> 8081
    Disponibilidad  -> 8082
    Habitaciones    -> 8083
    Empleados       -> 8084
    Huespedes       -> 8085
    Reservas        -> 8086
    Check-In        -> 8087
    Check-Out       -> 8088
    Pago            -> 8089
    Factura         -> 8090
    Eureka          -> 8761

### Funcionalidades
    
CRUD en cada microservicio
- Crean instancias
- Leer y Buscar instancias por ID y/o RUN.
- Actualizar instancias
- Eliminar instancias

### Tecnologias utilizadas

### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation

### Base de Datos
- MySQL
- Flyway

### Utilidades y Dependencias
- Lombok
- WebClient
- Maven


### Ejecucion
#### Programas utilizados
- Laragon
- Postman

#### Pasos Realizados
1. Clonar Repositorio

```bash
git clone https://github.com/CodedbyMeTheGreatest/Reserva-de-Hoteles.git
```

2. Iniciar MySQL desde Laragon

3. Crear la base de datos correspondiente

```sql
DROP DATABASE IF EXISTS db_hoteleria_hoteles;
DROP DATABASE IF EXISTS db_hoteleria_habitaciones;
DROP DATABASE IF EXISTS db_hoteleria_pagos;
DROP DATABASE IF EXISTS db_hoteleria_reservas;
DROP DATABASE IF EXISTS db_hoteleria_disponibilidades;
DROP DATABASE IF EXISTS db_hoteleria_huespedes;
DROP DATABASE IF EXISTS db_hoteleria_empleados;
DROP DATABASE IF EXISTS db_hoteleria_facturas;
DROP DATABASE IF EXISTS db_hoteleria_check_in;
DROP DATABASE IF EXISTS db_hoteleria_check_out;

CREATE DATABASE db_hoteleria_hoteles;
CREATE DATABASE db_hoteleria_habitaciones;
CREATE DATABASE db_hoteleria_pagos;
CREATE DATABASE db_hoteleria_reservas;
CREATE DATABASE db_hoteleria_disponibilidades;
CREATE DATABASE db_hoteleria_huespedes;
CREATE DATABASE db_hoteleria_empleados;
CREATE DATABASE db_hoteleria_facturas;
CREATE DATABASE db_hoteleria_check_in;
CREATE DATABASE db_hoteleria_check_out;
```

4. Ejecutar el proyecto

```bash
mvn spring-boot:run
```


### Integrantes 
    Jean Lefiman
    Corina Urrutia  
    

