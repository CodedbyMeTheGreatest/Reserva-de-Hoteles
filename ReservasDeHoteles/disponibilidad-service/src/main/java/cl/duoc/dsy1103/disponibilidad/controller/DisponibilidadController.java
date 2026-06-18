package cl.duoc.dsy1103.disponibilidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@Slf4j
@RequestMapping("/api/disponibilidades")
@Tag(name = "Disponibilidad", description = "Gestion de disponibilidades de habitaciones")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    private void agregarLinks(DisponibilidadResponse disponibilidad){
        disponibilidad.add(linkTo(methodOn(DisponibilidadController.class).buscarDisponibilidadPorId(disponibilidad.getIdDisponibilidad())).withSelfRel());
        disponibilidad.add(linkTo(methodOn(DisponibilidadController.class).buscarDisponibilidades()).withRel("todas-las-disponibilidades"));
        disponibilidad.add(linkTo(methodOn(DisponibilidadController.class).eliminarDisponibilidad(disponibilidad.getIdDisponibilidad())).withRel("eliminar"));
    }

    @GetMapping
    @Operation(summary = "Obtener disponibilidades", description = "Obtiene todas las disponibilidades de habitacion ingresadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DisponibilidadResponse.class)
            )
        )
    })
    public ResponseEntity<CollectionModel<DisponibilidadResponse>> buscarDisponibilidades(){
        log.info("GET /api/disponibilidades");
        List<DisponibilidadResponse> disponibilidades = disponibilidadService.buscarDisponibilidades();
        disponibilidades.forEach(this::agregarLinks);
        CollectionModel<DisponibilidadResponse> collection = CollectionModel.of(disponibilidades,
                linkTo(methodOn(DisponibilidadController.class).buscarDisponibilidades()).withSelfRel());
        return ResponseEntity.ok(collection);
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
        DisponibilidadResponse disponibilidad = disponibilidadService.buscarDisponibilidadPorId(idDisponibilidad);
        agregarLinks(disponibilidad);
        return ResponseEntity.ok(disponibilidad);
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
        DisponibilidadResponse disponibilidad = disponibilidadService.crearDisponibilidad(request);
        agregarLinks(disponibilidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(disponibilidad);
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
        DisponibilidadResponse disponibilidad = disponibilidadService.actualizarDisponibilidad(idDisponibilidad, request);
        agregarLinks(disponibilidad);
        return ResponseEntity.ok(disponibilidad);
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
