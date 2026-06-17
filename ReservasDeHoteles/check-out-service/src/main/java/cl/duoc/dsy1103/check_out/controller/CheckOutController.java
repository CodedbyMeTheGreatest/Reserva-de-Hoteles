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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check_out")
@Slf4j
public class CheckOutController {
    private final CheckOutService checkOutService;

    CheckOutController(CheckOutService checkOutService) {
        this.checkOutService = checkOutService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los check out", description = "Retorna todos los check out existentes con su información")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckOutResponse.class)))
    })
    public ResponseEntity<List<CheckOutResponse>> obtenerCheckOut(){
        log.info("GET /api/check_out");
        return ResponseEntity.ok(checkOutService.obtenerCheckOut());
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
        return ResponseEntity.ok(checkOutService.buscarCheckOutPorId(id));
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
        return ResponseEntity.ok(checkOutService.buscarCheckOutPorIdReserva(idReserva));
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
        return ResponseEntity.status(HttpStatus.CREATED).body(checkOutService.agregarCheckOut(request));
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
        return ResponseEntity.ok(checkOutService.actualizarCheckOut(id, updateRequest));
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
}
