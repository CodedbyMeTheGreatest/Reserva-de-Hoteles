package cl.duoc.dsy1103.disponibilidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadRequest;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadResponse;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadUpdateRequest;
import cl.duoc.dsy1103.disponibilidad.service.DisponibilidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@Slf4j
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @GetMapping
    @Operation(summary = "Obtener disponibilidades", description = "Obtiene todas las disponibilidades de habitacion ingresadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DisponibilidadResponse.class)
            )
        )
    })
    public ResponseEntity<List<DisponibilidadResponse>> buscarDisponibilidades(){
        log.info("GET /api/disponibilidades");
        return ResponseEntity.ok().body(disponibilidadService.buscarDisponibilidades());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener disponibilidad por ID", description = "Obtiene una disponibilidad especifica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DisponibilidadResponse.class))),
        @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada"),
        
    })
    public ResponseEntity<DisponibilidadResponse> buscarDisponibilidadPorId(@PathVariable("id")Long idDisponibilidad){
        log.info("GET /api/disponibilidades/{}", idDisponibilidad);
        return ResponseEntity.ok().body(disponibilidadService.buscarDisponibilidadPorId(idDisponibilidad));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva disponibilidad", description = "Agrega una nueva disponibilidad de habitacion al sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DisponibilidadResponse.class))),
        @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada"),
        @ApiResponse(responseCode = "400", description = "Ingreso de datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DisponibilidadResponse> crearDisponibilidad (@Valid @RequestBody DisponibilidadRequest request) {
        log.info("POST /api/disponibilidades/crearDisponibilidad");
        return ResponseEntity.status(HttpStatus.CREATED).body(disponibilidadService.crearDisponibilidad(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar disponibilidad", description = "Actualiza uno o todos los campos de una disponibilidad ingresada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DisponibilidadResponse.class))),
        @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada"),
        @ApiResponse(responseCode = "400", description = "Ingreso de datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DisponibilidadResponse> actualizarDisponibilidad (@PathVariable("id") Long idDisponibilidad, @Valid @RequestBody DisponibilidadUpdateRequest request){
        log.info("PUT /api/disponibilidades/actualizarDisponibilidad/{}", idDisponibilidad);
        return ResponseEntity.ok().body(disponibilidadService.actualizarDisponibilidad(idDisponibilidad, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una disponibilidad por su ID", description = "Elimina una disponibilidad existente por la ID ingresada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Disponibilidad eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Disponibilidad no encontrada")
    })
    public ResponseEntity<Void> eliminarDisponibilidad (@PathVariable("id") Long idDisponibilidad){
        log.info("DELETE /api/disponibilidades/eliminarDisponibilidad/{}",idDisponibilidad);
        disponibilidadService.eliminarDisponibilidad(idDisponibilidad);
        return ResponseEntity.noContent().build();
    }

}
