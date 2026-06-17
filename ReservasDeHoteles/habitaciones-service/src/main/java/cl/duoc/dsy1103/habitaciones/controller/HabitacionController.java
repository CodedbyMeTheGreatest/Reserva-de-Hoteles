package cl.duoc.dsy1103.habitaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.dsy1103.habitaciones.dto.ApiErrorResponse;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionRequest;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionResponse;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionUpdateRequest;
import cl.duoc.dsy1103.habitaciones.service.HabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/habitaciones")
@Slf4j
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping
    @Operation(summary = "Obtener todas las habitaciones", description = "Obtiene todas las habitaciones existentes en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HabitacionResponse.class)))
    })
    public ResponseEntity<List<HabitacionResponse>> buscarHabitaciones(){
        log.info("GET /api/habitaciones");
        return ResponseEntity.ok().body(habitacionService.buscarHabitaciones());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una habitación por ID", description = "Obtiene una habitación específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HabitacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<HabitacionResponse> buscarHabitacionPorId(@PathVariable("id")Long idHabitacion){
        log.info("GET /api/habitaciones/{}", idHabitacion);
        return ResponseEntity.ok().body(habitacionService.buscarHabitacionPorId(idHabitacion));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva habitación", description = "Crea una nueva habitación en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Habitación creada exitosamente",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HabitacionResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<HabitacionResponse> crearHabitacion (@Valid @RequestBody HabitacionRequest request) {
        log.info("POST /api/habitaciones/crearHabitacion");
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacionService.crearHabitacion(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una habitación", description = "Actualiza una habitación existente en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Habitación actualizada exitosamente",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HabitacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<HabitacionResponse> actualizarHabitacion (@PathVariable("id") Long idHabitacion, @Valid @RequestBody HabitacionUpdateRequest request){
        log.info("PUT /api/habitaciones/actualizarHabitacion/{}", idHabitacion);
        return ResponseEntity.ok().body(habitacionService.actualizarHabitacion(idHabitacion, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una habitación", description = "Elimina una habitación existente en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Habitación eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarHabitacion (@PathVariable("id") Long idHabitacion){
        log.info("DELETE /api/habitaciones/eliminarHabitacion/{}",idHabitacion);
        habitacionService.eliminarHabitacion(idHabitacion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("numero/{numero}")
    @Operation(summary = "Obtener una habitación por número", description = "Obtiene una habitación específica por su número")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HabitacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada")
    })
    public ResponseEntity<HabitacionResponse> buscarHabitacionPorNumero(@PathVariable("numero") String numero){
        log.info("GET /api/habitaciones/numero/{}", numero);
        return ResponseEntity.ok().body(habitacionService.buscarHabitacionPorNumero(numero));
    }
    
}
