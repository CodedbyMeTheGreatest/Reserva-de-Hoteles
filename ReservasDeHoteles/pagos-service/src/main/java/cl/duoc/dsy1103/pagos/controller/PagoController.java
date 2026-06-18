package cl.duoc.dsy1103.pagos.controller;

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

import cl.duoc.dsy1103.pagos.dto.ApiErrorResponse;
import cl.duoc.dsy1103.pagos.dto.PagoRequest;
import cl.duoc.dsy1103.pagos.dto.PagoResponse;
import cl.duoc.dsy1103.pagos.dto.PagoUpdateRequest;
import cl.duoc.dsy1103.pagos.service.PagoService;
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
@Slf4j
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    private void agregarLinks(PagoResponse pago){
        pago.add(linkTo(methodOn(PagoController.class).buscarPagoPorId(pago.getIdPago())).withSelfRel());
        pago.add(linkTo(methodOn(PagoController.class).buscarPagos()).withRel("todos-los-pagos"));
        pago.add(linkTo(methodOn(PagoController.class).eliminarPago(pago.getIdPago())).withRel("eliminar"));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pagos", description = "Obtiene todos los pagos existentes en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = PagoResponse.class)))
    })
    public ResponseEntity<CollectionModel<PagoResponse>> buscarPagos(){
        log.info("GET /api/pagos");
        List<PagoResponse> pagos = pagoService.buscarPagos();
        pagos.forEach(this::agregarLinks);
        CollectionModel<PagoResponse> collection = CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).buscarPagos()).withSelfRel());
        return ResponseEntity.ok(collection);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pago por ID", description = "Obtiene un pago específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = PagoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoResponse> buscarPagoPorId(@PathVariable("id") Long id){
        log.info("GET /api/pagos/{}", id);
        PagoResponse pago = pagoService.buscarPagoPorId(id);
        agregarLinks(pago);
        return ResponseEntity.ok(pago);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pago", description = "Crea un nuevo pago para la reserva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago creado exitosamente",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = PagoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Habitacion o Huesped no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PagoResponse> crearPago (@Valid @RequestBody PagoRequest request) {
        log.info("POST /api/pagos/crearPago");
        PagoResponse pago = pagoService.crearPago(request);
        agregarLinks(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago", description = "Actualiza un pago existente en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = PagoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PagoResponse> actualizarPago (@PathVariable("id") Long idPago, @Valid @RequestBody PagoUpdateRequest request){
        log.info("PUT /api/pagos/actualizarPago/{}", idPago);
        PagoResponse pago = pagoService.buscarPagoPorId(idPago);
        agregarLinks(pago);
        return ResponseEntity.ok(pago);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago", description = "Elimina un pago existente en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminarPago (@PathVariable("id") Long idPago){
        log.info("DELETE /api/pagos/eliminarPago/{}", idPago);
        pagoService.eliminarPago(idPago);
        return ResponseEntity.noContent().build();
    }
}
