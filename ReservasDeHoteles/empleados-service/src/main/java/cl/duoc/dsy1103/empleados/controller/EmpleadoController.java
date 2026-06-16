package cl.duoc.dsy1103.empleados.controller;

import cl.duoc.dsy1103.empleados.dto.EmpleadoRequest;
import cl.duoc.dsy1103.empleados.dto.EmpleadoResponse;
import cl.duoc.dsy1103.empleados.dto.EmpleadoUpdateRequest;
import cl.duoc.dsy1103.empleados.dto.ReservaResponse;
import cl.duoc.dsy1103.empleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@Slf4j
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los empleados", description = "Retorna una lista de empleados existentes en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class)))
    })
    public List<EmpleadoResponse> obtenerEmpleados(){
        log.info("GET /api/empleados");
        return empleadoService.obtenerEmpleados();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un empleado por una ID específica", description = "Retorna un empleado existente en la base de datos asociado a una ID ingresada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponse> buscarEmpleadoPorId(@PathVariable Long id){
        log.info("GET /api/empleados/id/{}", id);
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
    }

    @GetMapping("/run/{run}")
    @Operation(summary = "Obtener un empleado por un RUT específico", description = "Retorna un empleado existente en la base de datos asociado a un RUT ingresado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponse> buscarEmpleadoPorRut(@PathVariable String run){
        log.info("GET /api/empleados/run/{}", run);
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorRut(run));
    }

    @PostMapping
    @Operation(summary = "Agregar un empleado", description = "Agrega un empleado nuevo en la base de datos, validando la existencia del hotel al que pertenece")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empleado agregado", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Hotel no encontrado"),
        @ApiResponse(responseCode = "400", description = "Empleado ya existe o datos ingresados inválidos"),
        @ApiResponse(responseCode = "500", description = "Operación fallida")
    })
    public ResponseEntity<EmpleadoResponse> agregarEmpleado(@Valid @RequestBody EmpleadoRequest request){
        log.info("POST /api/empleados -> RUN: {}", request.getRun());
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.agregarEmpleado(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado", description = "Actualiza un empleado existente en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Operación fallida")
    })
    public ResponseEntity<EmpleadoResponse> actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoUpdateRequest updateRequest){
        log.info("PUT /api/empleados/{} -> RUN: {}", id,updateRequest.getRun());
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado", description = "Elimina un empleado existente de la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empleado eliminado existosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id){
        log.info("DELETE /api/empleado/{}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservas/{run}")
    @Operation(summary = "Obterner todas las reservas de un empleado", description = "Retorna todas las reservas que ha realizado un empleado, por medio de su RUN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación existosa"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado o Reservas no encontradas"),
        @ApiResponse(responseCode = "500", description = "Operación fallida")
    })
    public ResponseEntity<List<ReservaResponse>> obtenerReservasPorRunEmpleado(@PathVariable String run){
        log.info("GET /api/empleados/run/reservas/{} ", run);
        return ResponseEntity.ok(empleadoService.obtenerReservasPorRunEmpleado(run));
    }

}
