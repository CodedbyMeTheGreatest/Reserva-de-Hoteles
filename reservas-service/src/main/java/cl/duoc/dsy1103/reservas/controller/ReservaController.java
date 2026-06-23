package cl.duoc.dsy1103.reservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import cl.duoc.dsy1103.reservas.dto.ReservaRequest;
import cl.duoc.dsy1103.reservas.dto.ReservaResponse;
import cl.duoc.dsy1103.reservas.dto.ReservaUpdateRequest;
import cl.duoc.dsy1103.reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/reservas")
@Slf4j
@Tag(name = "Reservas", description = "Gestion de reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    private void agregarLinks(ReservaResponse reservaResponse) {
        reservaResponse.add(linkTo(methodOn(ReservaController.class).buscarReservaPorId(reservaResponse.getIdReserva())).withSelfRel());
        reservaResponse.add(linkTo(methodOn(ReservaController.class).buscarReservas()).withRel("todos-las-reservas"));
        reservaResponse.add(linkTo(methodOn(ReservaController.class).eliminarReserva(reservaResponse.getIdReserva())).withRel("eliminar"));
    }

    @GetMapping
    @Operation(summary = "Obtener reservas", description = "Obtiene todas las reservas ingresadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ReservaResponse.class)
            )
        )
    })
    public ResponseEntity<CollectionModel<ReservaResponse>> buscarReservas(){
        log.info("GET /api/reservas/buscarReservas");
        List<ReservaResponse> reservas = reservaService.buscarReservas();
        reservas.forEach(this::agregarLinks);
        CollectionModel<ReservaResponse> collection = CollectionModel.of(reservas,
                linkTo(methodOn(ReservaController.class).buscarReservas()).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reserva por ID", description = "Obtiene una reserva especifica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ReservaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrado"),
        
    })
    public ResponseEntity<ReservaResponse> buscarReservaPorId(@PathVariable("id") Long id){
        log.info("GET /api/reservas/{}", id);
        ReservaResponse reserva = reservaService.buscarReservaPorId(id);
        agregarLinks(reserva);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reserva", description = "Agrega una nueva reserva al sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ReservaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
        @ApiResponse(responseCode = "400", description = "Ingreso de datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaResponse> crearReserva (@Valid @RequestBody ReservaRequest request) {
        log.info("POST /api/reservas/crearReserva");
        ReservaResponse reserva = reservaService.crearReserva(request);
        agregarLinks(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una reserva", description = "Actualiza uno o todos los campos de una reserva existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ReservaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
        @ApiResponse(responseCode = "400", description = "Ingreso de datos invalidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReservaResponse> actualizarReserva (@PathVariable("id") Long idReserva, @Valid @RequestBody ReservaUpdateRequest request){
        log.info("PUT /api/reservas/actualizarReserva/{}", idReserva);
        ReservaResponse reserva = reservaService.actualizarReserva(idReserva, request);
        agregarLinks(reserva);
        return ResponseEntity.ok(reserva);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una reserva por su ID", description = "Elimina una reserva existente por la ID ingresada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    public ResponseEntity<Void> eliminarReserva (@PathVariable("id") Long idReserva){
        log.info("DELETE /api/reservas/eliminarReserva/{}", idReserva);
        reservaService.eliminarReserva(idReserva);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empleado/{run}")
    @Operation(summary = "Obtener reserva por run", description = "Obtiene una reserva por el run del empleado que la realizo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ReservaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrado"),
    })    
    public ResponseEntity<List<ReservaResponse>> buscarReservasPorEmpleado(@PathVariable("run") String run){
        log.info("GET /api/reservas/empleado/{}", run);
        List<ReservaResponse> reservas = reservaService.buscarReservasPorEmpleado(run);
        reservas.forEach(this::agregarLinks);
        return ResponseEntity.ok(reservas);
    }
}
