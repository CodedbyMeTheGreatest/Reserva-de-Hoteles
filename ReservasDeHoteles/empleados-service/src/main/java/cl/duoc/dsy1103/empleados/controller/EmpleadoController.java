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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Empleados", description = "Gestión de Empleados")
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
    public ResponseEntity<CollectionModel<EmpleadoResponse>> obtenerEmpleados(){
        log.info("GET /api/empleados");
        List<EmpleadoResponse> empleados = empleadoService.obtenerEmpleados();
        empleados.forEach(this::agregarLinks);
        CollectionModel<EmpleadoResponse> collection = CollectionModel.of(empleados, 
            linkTo(methodOn(EmpleadoController.class).obtenerEmpleados()).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un empleado por una ID", description = "Retorna un empleado existente en la base de datos asociado a una ID ingresada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponse> buscarEmpleadoPorId(@PathVariable Long id){
        log.info("GET /api/empleados/id/{}", id);
        EmpleadoResponse encontrado = empleadoService.buscarEmpleadoPorId(id);
        agregarLinks(encontrado);
        return ResponseEntity.ok(encontrado);
    }

    @GetMapping("/run/{run}")
    @Operation(summary = "Obtener un empleado por un RUN", description = "Retorna un empleado existente en la base de datos asociado a un RUN ingresado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponse> buscarEmpleadoPorRun(@PathVariable String run){
        log.info("GET /api/empleados/run/{}", run);
        EmpleadoResponse encontrado = empleadoService.buscarEmpleadoPorRun(run);
        agregarLinks(encontrado);
        return ResponseEntity.ok(encontrado);
    }

    @PostMapping
    @Operation(summary = "Agregar un empleado", description = "Agrega un empleado nuevo en la base de datos, validando la existencia del hotel al que pertenece")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empleado agregado", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ingreso de datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Operación fallida")
    })
    public ResponseEntity<EmpleadoResponse> agregarEmpleado(@Valid @RequestBody EmpleadoRequest request){
        log.info("POST /api/empleados -> RUN: {}", request.getRun());
        EmpleadoResponse agregado = empleadoService.agregarEmpleado(request);
        agregarLinks(agregado);
        return ResponseEntity.status(HttpStatus.CREATED).body(agregado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado", description = "Actualiza un empleado existente en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = EmpleadoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Datos no encontrados"),
        @ApiResponse(responseCode = "500", description = "Operación fallida")
    })
    public ResponseEntity<EmpleadoResponse> actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoUpdateRequest updateRequest){
        log.info("PUT /api/empleados/{} -> RUN: {}", id,updateRequest.getRun());
        EmpleadoResponse actualizado = empleadoService.actualizarEmpleado(id, updateRequest);
        agregarLinks(actualizado);
        return ResponseEntity.ok(actualizado);
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

    public void agregarLinks(EmpleadoResponse response){
        response.add(
            linkTo(methodOn(EmpleadoController.class).buscarEmpleadoPorId(response.getIdEmpleado())).withSelfRel(),
            linkTo(methodOn(EmpleadoController.class).buscarEmpleadoPorRun(response.getRun())).withSelfRel(),
            linkTo(methodOn(EmpleadoController.class).obtenerEmpleados()).withRel("all"));
    }
}
