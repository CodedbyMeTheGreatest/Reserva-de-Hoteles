# Sistema de Gestion de Reservas de Hoteles

## Índice

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Estudiantes](#estudiantes)
- [Listado de Microservicios](#listado-de-microservicios-implementados)
- [Bases de Datos](#bases-de-datos-mysql)
- [Rutas del Gateway](#rutas-principales-del-gateway)
- [Documentación Swagger](#documentación-swagger)
- [Instrucciones de Ejecución](#instrucciones-de-ejecución)
  - [Requisitos Previos](#requisitos-previos)
  - [Ejecución con Docker](#ejecución-con-docker-recomendada)
  - [Ejecución Sin Docker](#ejecución-sin-docker)
  - [Comandos de Verificación](#comandos-de-verificación)
- [Limpiar el Entorno](#limpiar-el-entorno)
- [Tests Unitarios](#tests-unitarios)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Programas Utilizados](#programas-utilizados)
- [Estructura del Repositorio](#estructura-del-repositorio)


## Descripción del Proyecto

Este proyecto consiste en un **sistema de gestión hoteletera** basado en una arquitectura de **microservicios**. Su objetivo es administrar de forma eficiente y modular los procesos clave de un hotel, incluyendo la gestión de:

- **Hoteles** y sus instalaciones
- **Habitaciones** y su disponibilidad
- **Empleados** del hotel
- **Huéspedes** (clientes)
- **Reservas** de habitaciones
- **Check-In** y **Check-Out**
- **Pagos** y **Facturación**

Cada microservicio es **independiente**, tiene su propia base de datos **MySQL** y se comunica con los demás a través de **APIs REST** y **descubrimiento de servicios** con **Eureka**.

El sistema está diseñado para ser **escalable**, **mantenible** y **fácil de desplegar** en entornos de desarrollo, pruebas y producción mediante **Docker**.

## Estudiantes

- **Jean Lefiman** - je.lefiman@duocuc.cl
- **Corina Urrutia** - cor.urrutia@duocuc.cl

**Docente:** José Miguel Candia

**Asignatura:** Desarrollo Full Stack I

---

## Listado de Microservicios Implementados

| # | Microservicio | Puerto | Descripción |
|---|---------------|--------|-------------|
| 1 | **Eureka Server** | `8761` | Servicio de descubrimiento y registro |
| 2 | **API Gateway** | `8080` | Punto de entrada único para todas las peticiones |
| 3 | **Hoteles Service** | `8081` | Gestión de hoteles y sus datos |
| 4 | **Disponibilidad Service** | `8082` | Verificación de disponibilidad de habitaciones |
| 5 | **Habitaciones Service** | `8083` | Gestión de habitaciones por hotel |
| 6 | **Empleados Service** | `8084` | Gestión de empleados del hotel |
| 7 | **Huéspedes Service** | `8085` | Gestión de clientes/huéspedes |
| 8 | **Reservas Service** | `8086` | Creación y gestión de reservas |
| 9 | **Check-In Service** | `8087` | Registro de entrada de huéspedes |
| 10 | **Check-Out Service** | `8088` | Registro de salida de huéspedes |
| 11 | **Pagos Service** | `8089` | Procesamiento de pagos |
| 12 | **Facturas Service** | `8090` | Generación y gestión de facturas |

### Bases de Datos (MySQL)

Cada microservicio tiene su propia base de datos:

| Microservicio | Base de Datos |
|---------------|---------------|
| Hoteles Service | `db_hoteleria_hoteles` |
| Disponibilidad Service | `db_hoteleria_disponibilidades` |
| Habitaciones Service | `db_hoteleria_habitaciones` |
| Empleados Service | `db_hoteleria_empleados` |
| Huéspedes Service | `db_hoteleria_huespedes` |
| Reservas Service | `db_hoteleria_reservas` |
| Check-In Service | `db_hoteleria_check_in` |
| Check-Out Service | `db_hoteleria_check_out` |
| Pagos Service | `db_hoteleria_pagos` |
| Facturas Service | `db_hoteleria_facturas` |

---

## Rutas Principales del Gateway

El **API Gateway** expone las siguientes rutas para acceder a los microservicios:

| Servicio | Ruta en Gateway | Ejemplo de uso |
|----------|-----------------|----------------|
| Hoteles | `/api/hoteles/**` | `GET /api/hoteles/1` |
| Disponibilidad | `/api/disponibilidades/**` | `GET /api/disponibilidades/fechas` |
| Habitaciones | `/api/habitaciones/**` | `POST /api/habitaciones` |
| Empleados | `/api/empleados/**` | `GET /api/empleados/run/12345678-9` |
| Huéspedes | `/api/huespedes/**` | `POST /api/huespedes` |
| Reservas | `/api/reservas/**` | `POST /api/reservas` |
| Check-In | `/api/check_in/**` | `POST /api/check_in` |
| Check-Out | `/api/check_out/**` | `POST /api/check_out` |
| Pagos | `/api/pagos/**` | `POST /api/pagos` |
| Facturas | `/api/facturas/**` | `GET /api/facturas/folio/F1` |

### Ejemplo de Peticiones

```bash
# Obtener todas las facturas
curl -X GET http://localhost:8080/api/facturas
```
```bash
# Obtener un huésped por RUN
curl -X GET http://localhost:8080/api/huespedes/run/12345678-9
```
```bash
# Crear una nueva reserva
curl -X POST http://localhost:8080/api/reservas \
  -H "Content-Type: application/json" \
  -d '{
    "idHabitacion": 1,
    "runHuesped": "12345678-9",
    "fechaIngreso": "2026-07-01",
    "fechaSalida": "2026-07-05",
    "idEmpleado": 1
  }'
```
```bash
# Crear un check-in
curl -X POST http://localhost:8080/api/check_in \
  -H "Content-Type: application/json" \
  -d '{
    "idReserva": 1,
    "runEmpleado": "12345678-9"
  }'
```
```bash
# Procesar un pago
curl -X POST http://localhost:8080/api/pagos \
  -H "Content-Type: application/json" \
  -d '{
    "idReserva": 1,
    "runHuesped": "12345678-9",
    "metodoPago": "TARJETA",
    "monto": 150000
  }'
```

## Documentación Swagger Directo a cada Microservicio

| Servicio | Swagger UI | OpenAPI |
|----------|------------|---------|
| **Hoteles** | [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) | [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs) |
| **Disponibilidad** | [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html) | [http://localhost:8082/v3/api-docs](http://localhost:8082/v3/api-docs) |
| **Habitaciones** | [http://localhost:8083/swagger-ui.html](http://localhost:8083/swagger-ui.html) | [http://localhost:8083/v3/api-docs](http://localhost:8083/v3/api-docs) |
| **Empleados** | [http://localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html) | [http://localhost:8084/v3/api-docs](http://localhost:8084/v3/api-docs) |
| **Huéspedes** | [http://localhost:8085/swagger-ui.html](http://localhost:8085/swagger-ui.html) | [http://localhost:8085/v3/api-docs](http://localhost:8085/v3/api-docs) |
| **Reservas** | [http://localhost:8086/swagger-ui.html](http://localhost:8086/swagger-ui.html) | [http://localhost:8086/v3/api-docs](http://localhost:8086/v3/api-docs) |
| **Check-In** | [http://localhost:8087/swagger-ui.html](http://localhost:8087/swagger-ui.html) | [http://localhost:8087/v3/api-docs](http://localhost:8087/v3/api-docs) |
| **Check-Out** | [http://localhost:8088/swagger-ui.html](http://localhost:8088/swagger-ui.html) | [http://localhost:8088/v3/api-docs](http://localhost:8088/v3/api-docs) |
| **Pagos** | [http://localhost:8089/swagger-ui.html](http://localhost:8089/swagger-ui.html) | [http://localhost:8089/v3/api-docs](http://localhost:8089/v3/api-docs) |
| **Facturas** | [http://localhost:8090/swagger-ui.html](http://localhost:8090/swagger-ui.html) | [http://localhost:8090/v3/api-docs](http://localhost:8090/v3/api-docs) |

## Instrucciones de Ejecución

### Requisitos Previos

- Docker Desktop (versión 20.10+)

- Java 17 (opcional, solo para desarrollo local)

- Maven 3.9+ (opcional, solo para desarrollo local)

### Ejecución Docker (RECOMENDADA)

```bash
# 1. Clonar el repositorio
git clone https://github.com/CodedbyMeTheGreatest/Reserva-de-Hoteles.git

#(Opcional - Si Aplica)
cd Reserva-de-Hoteles 

# 2. Copiar el archivo de variables de entorno
cp .env.example .env

# 4. Construir y levantar todos los servicios
docker-compose up --build
```

### Comandos de Verificación
```bash 
# Ver estado de Eureka
curl http://localhost:8761

# Ver health del Gateway
curl http://localhost:8080/actuator/health

# Probar un endpoint
curl http://localhost:8080/api/huespedes
```

### Ejecución Sin Docker
-> **Requisito:** Tener MySql instalado localmente y corriendo en puerto 3306

```bash
# 1. Crear las bases de datos manualmente en MySQL
mysql -u root -p
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
EXIT;

# 2. Levantar Eureka Server
cd eureka-server
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 3. Levantar Gateway (en otra terminal)
cd ../api-gateway
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 4. Levantar cada microservicio (en terminales separadas)
cd ../hoteles-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

cd ../disponibilidad-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Repetir para cada microservicio...
```

### Limpiar el Entorno
```bash
# Detener y eliminar todos los contenedores
docker-compose down

# Eliminar también volúmenes (datos de BD)
docker-compose down -v

# Eliminar contenedores, redes, imágenes y volúmenes no utilizados
docker system prune -a

# Limpiar también volúmenes huérfanos
docker system prune -a --volumes
```

### Reiniciar todo desde Cero

```bash
# 1. Limpiar todo
docker-compose down -v
docker system prune -a --volumes

# 2. Compilar
mvn clean package -DskipTests

# 3. Reconstruir y levantar
docker-compose up --build
```

### Test Unitarios
```bash
# Ejecutar tests de un servicio específico
cd facturas-service
mvn test

# Ejecutar todos los tests del proyecto
mvn clean test
```

## Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 17 | Lenguaje de programación |
| **Spring Boot** | 3.4.0 | Framework principal |
| **Spring Cloud** | 2025.1.2 | Microservicios y descubrimiento |
| **Spring Cloud Gateway** | 2025.1.2 | API Gateway |
| **Eureka Server/Client** | 2025.1.2 | Descubrimiento de servicios |
| **Spring Data JPA** | 3.4.0 | ORM y acceso a datos |
| **MySQL** | 8.0 | Base de datos relacional |
| **Docker** | 20.10+ | Contenerización |
| **Docker Compose** | 2.0+ | Orquestación de contenedores |
| **Maven** | 3.9+ | Gestión de dependencias |
| **SpringDoc OpenAPI** | 2.3.0 | Documentación Swagger |
| **Flyway** | 10.0+ | Migraciones de base de datos |
| **Lombok** | 1.18.30 | Reducción de código boilerplate |
| **Mockito** | 5.0+ | Tests unitarios |
| **JUnit** | 5.10+ | Framework de pruebas |


### Ejecucion
#### Programas utilizados
- Docker
- Postman
- Laragon
    
## Estructura del Repositorio
```plaintext
Reserva-de-Hoteles/
├── .vscode/
│   └── launch.json
├── api-gateway/
│   ├── .mvn/
│   │   └── wrapper/
│   │       └── maven-wrapper.properties
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── api_gateway/
│   │   │   │                   └── ApiGatewayApplication.java
│   │   │   └── resources/
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── api_gateway/
│   │                           └── ApiGatewayApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── check-in-service/
│   ├── .mvn/
│   │   └── wrapper/
│   │       └── maven-wrapper.properties
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── check_in/
│   │   │   │                   ├── client/
│   │   │   │                   │   ├── EmpleadoClient.java
│   │   │   │                   │   └── ReservaClient.java
│   │   │   │                   ├── config/
│   │   │   │                   │   ├── SwaggerConfig.java
│   │   │   │                   │   └── WebClientConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── CheckInController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── CheckInRequest.java
│   │   │   │                   │   ├── CheckInResponse.java
│   │   │   │                   │   ├── CheckInUpdateRequest.java
│   │   │   │                   │   ├── EmpleadoResponse.java
│   │   │   │                   │   └── ReservaResponse.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   ├── BadRequestException.java
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── CheckInMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── CheckIn.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── CheckInRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── CheckInService.java
│   │   │   │                   └── CheckInServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_checkin.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── check_in/
│   │                           ├── service/
│   │                           │   └── CheckInServiceTest.java
│   │                           └── CheckInServiceApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── check-out-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── check_out/
│   │   │   │                   ├── client/
│   │   │   │                   │   ├── EmpleadoClient.java
│   │   │   │                   │   └── ReservaClient.java
│   │   │   │                   ├── config/
│   │   │   │                   │   ├── SwaggerConfig.java
│   │   │   │                   │   └── WebClientConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── CheckOutController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── CheckOutRequest.java
│   │   │   │                   │   ├── CheckOutResponse.java
│   │   │   │                   │   ├── CheckOutUpdateRequest.java
│   │   │   │                   │   ├── EmpleadoResponse.java
│   │   │   │                   │   └── ReservaResponse.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   ├── BadRequestException.java
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── CheckOutMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── CheckOut.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── CheckOutRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── CheckOutService.java
│   │   │   │                   └── CheckOutServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_check_out.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── check_out/
│   │                           ├── service/
│   │                           │   └── CheckOutServiceTest.java
│   │                           └── CheckOutServiceApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── disponibilidad-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── disponibilidad/
│   │   │   │                   ├── config/
│   │   │   │                   │   └── SwaggerConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── DisponibilidadController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── DisponibilidadRequest.java
│   │   │   │                   │   ├── DisponibilidadResponse.java
│   │   │   │                   │   └── DisponibilidadUpdateRequest.java
│   │   │   │                   ├── enums/
│   │   │   │                   │   └── EstadoDisponibilidad.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── DisponibilidadMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── Disponibilidad.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── DisponibilidadRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── DisponibilidadService.java
│   │   │   │                   └── DisponibilidadServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_disponibilidades.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── disponibilidad/
│   │                           ├── service/
│   │                           │   └── DisponibilidadServiceTest.java
│   │                           └── DisponibilidadServiceApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── empleados-service/
│   ├── .mvn/
│   │   └── wrapper/
│   │       └── maven-wrapper.properties
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── empleados/
│   │   │   │                   ├── client/
│   │   │   │                   │   ├── HotelClient.java
│   │   │   │                   │   └── ReservaClient.java
│   │   │   │                   ├── config/
│   │   │   │                   │   ├── SwaggerConfig.java
│   │   │   │                   │   └── WebClientConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── EmpleadoController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── EmpleadoRequest.java
│   │   │   │                   │   ├── EmpleadoResponse.java
│   │   │   │                   │   ├── EmpleadoUpdateRequest.java
│   │   │   │                   │   ├── HotelResponse.java
│   │   │   │                   │   └── ReservaResponse.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   ├── BadRequestException.java
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── EmpleadoMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── Empleado.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── EmpleadoRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── EmpleadoService.java
│   │   │   │                   └── EmpleadosServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_empleados.sql
│   │   │       │       └── V2__intial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── empleados/
│   │                           ├── service/
│   │                           │   └── EmpleadoServiceTest.java
│   │                           └── EmpleadosServicesApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── eureka-server/
│   ├── .mvn/
│   │   └── wrapper/
│   │       └── maven-wrapper.properties
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── cl/
│   │       │       └── duoc/
│   │       │           └── dsy1103/
│   │       │               └── eureka/
│   │       │                   └── EurekaServerApplication.java
│   │       └── resources/
│   │           ├── application-dev.yaml
│   │           ├── application-docker.yaml
│   │           └── application.yaml
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── facturas-service/
│   ├── .mvn/
│   │   └── wrapper/
│   │       └── maven-wrapper.properties
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── facturas/
│   │   │   │                   ├── client/
│   │   │   │                   │   ├── CheckInClient.java
│   │   │   │                   │   ├── CheckOutClient.java
│   │   │   │                   │   ├── HuespedClient.java
│   │   │   │                   │   ├── PagoClient.java
│   │   │   │                   │   └── ReservaClient.java
│   │   │   │                   ├── config/
│   │   │   │                   │   ├── SwaggerConfig.java
│   │   │   │                   │   └── WebClientConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── FacturaController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── CheckInResponse.java
│   │   │   │                   │   ├── CheckOutResponse.java
│   │   │   │                   │   ├── FacturaRequest.java
│   │   │   │                   │   ├── FacturaResponse.java
│   │   │   │                   │   ├── FacturaUpdateRequest.java
│   │   │   │                   │   ├── HuespedResponse.java
│   │   │   │                   │   ├── PagoResponse.java
│   │   │   │                   │   └── ReservaResponse.java
│   │   │   │                   ├── enums/
│   │   │   │                   │   ├── EstadoPago.java
│   │   │   │                   │   └── MetodoPago.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   ├── BadRequestException.java
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── FacturaMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── Factura.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── FacturaRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── FacturaService.java
│   │   │   │                   └── FacturasServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_facturas.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── facturas/
│   │                           ├── service/
│   │                           │   └── FacturaServiceTest.java
│   │                           └── FacturasServiceApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── habitaciones-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── habitaciones/
│   │   │   │                   ├── client/
│   │   │   │                   │   ├── DisponibilidadClient.java
│   │   │   │                   │   └── HotelClient.java
│   │   │   │                   ├── config/
│   │   │   │                   │   ├── SwaggerConfig.java
│   │   │   │                   │   └── WebClientConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── HabitacionController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── DisponibilidadResponse.java
│   │   │   │                   │   ├── HabitacionRequest.java
│   │   │   │                   │   ├── HabitacionResponse.java
│   │   │   │                   │   ├── HabitacionUpdateRequest.java
│   │   │   │                   │   └── HotelResponse.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   ├── ConflictException.java
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── HabitacionMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── Habitacion.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── HabitacionRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── HabitacionService.java
│   │   │   │                   └── HabitacionesServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_habitaciones.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── habitaciones/
│   │                           ├── service/
│   │                           │   └── HabitacionServiceTest.java
│   │                           └── HabitacionesServiceApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── hoteles-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── hotel/
│   │   │   │                   ├── config/
│   │   │   │                   │   └── SwaggerConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── HotelController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── HotelRequest.java
│   │   │   │                   │   ├── HotelResponse.java
│   │   │   │                   │   └── HotelUpdateRequest.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   ├── ConflictException.java
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── HotelMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── Hotel.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── HotelRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── HotelService.java
│   │   │   │                   └── HotelServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_hoteles.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── hotel/
│   │                           ├── service/
│   │                           │   └── HotelServiceTest.java
│   │                           └── HotelServiceApplicationTests.java
│   ├── target/
│   │   ├── classes/
│   │   │   ├── cl/
│   │   │   │   └── duoc/
│   │   │   │       └── dsy1103/
│   │   │   │           └── hotel/
│   │   │   │               ├── config/
│   │   │   │               │   └── SwaggerConfig.class
│   │   │   │               ├── controller/
│   │   │   │               │   └── HotelController.class
│   │   │   │               ├── dto/
│   │   │   │               │   ├── ApiErrorResponse.class
│   │   │   │               │   ├── ApiErrorResponse$ApiErrorResponseBuilder.class
│   │   │   │               │   ├── HotelRequest.class
│   │   │   │               │   ├── HotelResponse.class
│   │   │   │               │   ├── HotelResponse$HotelResponseBuilder.class
│   │   │   │               │   └── HotelUpdateRequest.class
│   │   │   │               ├── exception/
│   │   │   │               │   ├── ConflictException.class
│   │   │   │               │   └── GlobalHandlerException.class
│   │   │   │               ├── mapper/
│   │   │   │               │   └── HotelMapper.class
│   │   │   │               ├── model/
│   │   │   │               │   ├── Hotel.class
│   │   │   │               │   └── Hotel$HotelBuilder.class
│   │   │   │               ├── repository/
│   │   │   │               │   └── HotelRepository.class
│   │   │   │               ├── service/
│   │   │   │               │   └── HotelService.class
│   │   │   │               └── HotelServiceApplication.class
│   │   │   ├── db/
│   │   │   │   └── migration/
│   │   │   │       ├── V1__create_table_hoteles.sql
│   │   │   │       └── V2__initial_data.sql
│   │   │   ├── application-dev.yaml
│   │   │   ├── application-docker.yaml
│   │   │   └── application.yaml
│   │   └── test-classes/
│   │       └── cl/
│   │           └── duoc/
│   │               └── dsy1103/
│   │                   └── hotel/
│   │                       ├── service/
│   │                       │   └── HotelServiceTest.class
│   │                       └── HotelServiceApplicationTests.class
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── huespedes-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── huespedes/
│   │   │   │                   ├── config/
│   │   │   │                   │   └── SwaggerConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── HuespedController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── HuespedRequest.java
│   │   │   │                   │   ├── HuespedResponse.java
│   │   │   │                   │   └── HuespedUpdateRequest.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── HuespedMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── Huesped.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── HuespedRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── HuespedService.java
│   │   │   │                   └── HuespedesServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_huespedes.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── huespedes/
│   │                           ├── service/
│   │                           │   └── HuespedServiceTest.java
│   │                           └── HuespedesServiceApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── pagos-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── pagos/
│   │   │   │                   ├── client/
│   │   │   │                   │   ├── HabitacionClient.java
│   │   │   │                   │   └── HuespedClient.java
│   │   │   │                   ├── config/
│   │   │   │                   │   ├── SwaggerConfig.java
│   │   │   │                   │   └── WebClientConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── PagoController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── HabitacionResponse.java
│   │   │   │                   │   ├── HuespedResponse.java
│   │   │   │                   │   ├── PagoRequest.java
│   │   │   │                   │   ├── PagoResponse.java
│   │   │   │                   │   └── PagoUpdateRequest.java
│   │   │   │                   ├── enums/
│   │   │   │                   │   ├── EstadoPago.java
│   │   │   │                   │   └── MetodoPago.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── PagoMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── Pago.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── PagoRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── PagoService.java
│   │   │   │                   └── PagosServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_pagos.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── pagos/
│   │                           ├── service/
│   │                           │   └── PagoServiceTest.java
│   │                           └── PagosServiceApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── reservas-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── cl/
│   │   │   │       └── duoc/
│   │   │   │           └── dsy1103/
│   │   │   │               └── reservas/
│   │   │   │                   ├── client/
│   │   │   │                   │   ├── EmpleadoClient.java
│   │   │   │                   │   ├── HabitacionClient.java
│   │   │   │                   │   └── HuespedClient.java
│   │   │   │                   ├── config/
│   │   │   │                   │   ├── SwaggerConfig.java
│   │   │   │                   │   └── WebClientConfig.java
│   │   │   │                   ├── controller/
│   │   │   │                   │   └── ReservaController.java
│   │   │   │                   ├── dto/
│   │   │   │                   │   ├── ApiErrorResponse.java
│   │   │   │                   │   ├── EmpleadoResponse.java
│   │   │   │                   │   ├── HabitacionResponse.java
│   │   │   │                   │   ├── HuespedResponse.java
│   │   │   │                   │   ├── ReservaRequest.java
│   │   │   │                   │   ├── ReservaResponse.java
│   │   │   │                   │   └── ReservaUpdateRequest.java
│   │   │   │                   ├── exception/
│   │   │   │                   │   └── GlobalHandlerException.java
│   │   │   │                   ├── mapper/
│   │   │   │                   │   └── ReservaMapper.java
│   │   │   │                   ├── model/
│   │   │   │                   │   └── Reserva.java
│   │   │   │                   ├── repository/
│   │   │   │                   │   └── ReservaRepository.java
│   │   │   │                   ├── service/
│   │   │   │                   │   └── ReservaService.java
│   │   │   │                   └── ReservasServiceApplication.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       │       ├── V1__create_table_reservas.sql
│   │   │       │       └── V2__initial_data.sql
│   │   │       ├── application-dev.yaml
│   │   │       ├── application-docker.yaml
│   │   │       └── application.yaml
│   │   └── test/
│   │       └── java/
│   │           └── cl/
│   │               └── duoc/
│   │                   └── dsy1103/
│   │                       └── reservas/
│   │                           ├── service/
│   │                           │   └── ReservaServiceTest.java
│   │                           └── ReservasServiceApplicationTests.java
│   ├── .dockerignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
├── .env.example
├── .gitignore
├── compose.yaml
├── init-db.sql
└── README.md
```

