package cl.duoc.dsy1103.check_in.controller;

import cl.duoc.dsy1103.check_in.dto.CheckInRequest;
import cl.duoc.dsy1103.check_in.dto.CheckInResponse;
import cl.duoc.dsy1103.check_in.dto.CheckInUpdateRequest;
import cl.duoc.dsy1103.check_in.service.CheckInService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check_in")
@Slf4j
public class CheckInController {
    private final CheckInService checkInService;

    CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los check in", description = "Retorna todos los check in existentes en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckInResponse.class)))

    })
    public ResponseEntity<List<CheckInResponse>> obtenerCheckIns(){
        log.info("GET /api/check_in");
        return ResponseEntity.ok(checkInService.obtenerCheckIns());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un check in por ID", description = "Retorna un check in existente correspendiente a la ID ingresada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckInResponse.class))),
            @ApiResponse(responseCode = "404", description = "Check in no encontrado")
    })    
    public ResponseEntity<CheckInResponse> buscarCheckInPorId(@PathVariable Long id){
        log.info("GET /api/check_in/{}", id);
        return ResponseEntity.ok(checkInService.buscarCheckInPorId(id));
    }

    @PostMapping
    @Operation(summary = "Agregar un check in", description = "Agrega un check in nuevo, verificando la existencia de la reserva y el empleado relacionados a este")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check in creado existosamente", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckInResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ingreso de datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Reserva o Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Operación fallida")
    })    
    public ResponseEntity<CheckInResponse> agregarCheckIn(@Valid @RequestBody CheckInRequest request){
        log.info("GET /api/check_in -> ID: {}", request.getIdReserva());
        return ResponseEntity.status(HttpStatus.CREATED).body(checkInService.agregarCheckIn(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un check in", description = "Actualiza algunos o todos los campos de un check out existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check in actualizado exitosamente", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckInResponse.class))),
            @ApiResponse(responseCode = "404", description = "Datos no encontrados"),
            @ApiResponse(responseCode = "500", description = "Operación fallida")
    })    
    public ResponseEntity<CheckInResponse> actualizarCheckIn(@PathVariable Long id, @Valid @RequestBody CheckInUpdateRequest updateRequest){
        log.info("PUT /api/check_in/{}", id);
        return ResponseEntity.ok(checkInService.actualizarCheckIn(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un check in", description = "Elimina un check in existente correspondiente a una ID ingresada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Check in eliminado existosamente", 
                    content = @Content(mediaType = "application/json", 
                            schema = @Schema(implementation = CheckInResponse.class))),
            @ApiResponse(responseCode = "404", description = "Datos no encontrados")
    })    
    public ResponseEntity<Void> eliminarCheckIn(Long id){
        log.info("DELETE /api/check_in/{}", id);
        checkInService.eliminarCheckIn(id);
        return ResponseEntity.noContent().build();
    }
}
