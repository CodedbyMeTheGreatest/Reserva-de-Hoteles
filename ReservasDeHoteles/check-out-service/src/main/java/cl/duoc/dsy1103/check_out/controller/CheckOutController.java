package cl.duoc.dsy1103.check_out.controller;

import cl.duoc.dsy1103.check_out.dto.CheckOutRequest;
import cl.duoc.dsy1103.check_out.dto.CheckOutResponse;
import cl.duoc.dsy1103.check_out.dto.CheckOutUpdateRequest;
import cl.duoc.dsy1103.check_out.service.CheckOutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@RequestMapping("/api/check_out")
@Slf4j
public class CheckOutController {
    @Autowired
    private CheckOutService checkOutService;

    @GetMapping
    @Operation(summary = "Obtener todos los check out", description = "Retorna todos los check out existentes con su información")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckOutResponse.class)))
    })
    public ResponseEntity<CollectionModel<CheckOutResponse>> obtenerCheckOut(){
        log.info("GET /api/check_out");
        List<CheckOutResponse> checkOuts = checkOutService.obtenerCheckOut();
        checkOuts.forEach(this::agregarLinks);
        CollectionModel<CheckOutResponse> collection = CollectionModel.of(checkOuts,
                linkTo(methodOn(CheckOutController.class).obtenerCheckOut()).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener check out por ID", description = "Retorna un check out específico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación existosa", 
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CheckOutResponse.class))),
            @ApiResponse(responseCode = "404", description = "Check out no encontrado")
    })
    public ResponseEntity<CheckOutResponse> buscarCheckOutPorId(@PathVariable Long id){
        log.info("GET /api/check_out/{}", id);
        CheckOutResponse encontrado = checkOutService.buscarCheckOutPorId(id);
        agregarLinks(encontrado);
        return ResponseEntity.ok(encontrado);
    }

    @GetMapping("/reserva/{idReserva}")
    @Operation(summary = "Obtener check out por ID de reserva", description = "Retorna un check out existente con la ID de la reserva a la que pertenece")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación existosa", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckOutResponse.class))),
            @ApiResponse(responseCode = "404", description = "Check out no encontrado")
    })
    public ResponseEntity<CheckOutResponse> buscarCheckOutPorIdReserva(@PathVariable Long idReserva){
        log.info("GET /api/check_out/reserva/{}", idReserva);
        CheckOutResponse encontrado = checkOutService.buscarCheckOutPorIdReserva(idReserva);
        agregarLinks(encontrado);
        return ResponseEntity.ok(encontrado);
    }

    @PostMapping
    @Operation(summary = "Agregar el check out de una reserva", description = "Agrega un check out de una reserva existente, verificando la existencia del empleado y la reserva asociada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Check out agregado existosamente", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckOutResponse.class))),
            @ApiResponse(responseCode = "404", description = "Empleado o Reserva no encontrada"),
            @ApiResponse(responseCode = "400", description = "Ingreso de datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Operación fallida")
    })
    public ResponseEntity<CheckOutResponse> agregarCheckOut(@Valid @RequestBody CheckOutRequest request){
        log.info("GET /api/check_out -> ID: {}", request.getIdReserva());
        CheckOutResponse agregado = checkOutService.agregarCheckOut(request);
        agregarLinks(agregado);
        return ResponseEntity.status(HttpStatus.CREATED).body(agregado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un check out", description = "Actualiza algunos o todos los campos de un check out existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check out actualizado existosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CheckOutResponse.class))),
            @ApiResponse(responseCode = "404", description = "Datos no encontrados"),
            @ApiResponse(responseCode = "500", description = "Operación fallida")
    })
    public ResponseEntity<CheckOutResponse> actualizarCheckOut(@PathVariable Long id, @Valid @RequestBody CheckOutUpdateRequest updateRequest){
        log.info("PUT /api/check_out/{}", id);
        CheckOutResponse actualizado = checkOutService.actualizarCheckOut(id, updateRequest);
        agregarLinks(actualizado);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar check out por ID", description = "Elimina un check out existente por la ID ingresada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Check out eliminado existosamente"),
            @ApiResponse(responseCode = "404", description = "Check out no encontrado")
    })
    public ResponseEntity<Void> eliminarCheckOut(Long id){
        log.info("DELETE /api/check_out/{}", id);
        checkOutService.eliminarCheckOut(id);
        return ResponseEntity.noContent().build();
    }

    public void agregarLinks(CheckOutResponse response){
        response.add(
                linkTo(methodOn(CheckOutController.class).buscarCheckOutPorId(response.getId())).withSelfRel(),
                linkTo(methodOn(CheckOutController.class).buscarCheckOutPorIdReserva(response.getIdReserva())).withSelfRel(),
                linkTo(methodOn(CheckOutController.class).obtenerCheckOut()).withRel("all"));
    }
}
