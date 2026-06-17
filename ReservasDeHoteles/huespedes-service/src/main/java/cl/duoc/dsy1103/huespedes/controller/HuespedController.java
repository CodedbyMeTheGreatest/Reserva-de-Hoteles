package cl.duoc.dsy1103.huespedes.controller;

import cl.duoc.dsy1103.huespedes.dto.HuespedRequest;
import cl.duoc.dsy1103.huespedes.dto.HuespedResponse;
import cl.duoc.dsy1103.huespedes.dto.HuespedUpdateRequest;
import cl.duoc.dsy1103.huespedes.service.HuespedService;
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
@RequestMapping("/api/huespedes")
@Tag(name = "Huéspedes", description = "Gestión de Huéspedes")
@Slf4j
public class HuespedController {
    private final HuespedService huespedService;

    HuespedController(HuespedService huespedService) {
        this.huespedService = huespedService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los huespedes", description = "Retorna una lista de huespedes existentes en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HuespedResponse.class)))
    })    
    public ResponseEntity<CollectionModel<HuespedResponse>> obtenerHuespedes(){
        log.info("GET /api/huespedes");
        List<HuespedResponse> huespedes = huespedService.obtenerHuespedes();
        CollectionModel<HuespedResponse> collection = CollectionModel.of(huespedes, 
            linkTo(methodOn(HuespedController.class).obtenerHuespedes()).withSelfRel());
        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un huésped por una ID", description = "Retorna un huésped existente en la base de datos asociado a una ID ingresada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HuespedResponse.class))),
        @ApiResponse(responseCode = "404", description = "Huésped no encontrado")
    })    
    public ResponseEntity<HuespedResponse> buscarHuespedPorId(@PathVariable Long id){
        log.info("GET /api/huespedes/{}", id);
        HuespedResponse encontrado = huespedService.buscarHuespedPorId(id);
        agregarLinks(encontrado);
        return ResponseEntity.ok(encontrado);
    }

    @GetMapping("/run/{run}")
    @Operation(summary = "Obtener un huésped por un RUN", description = "Retorna un huésped existente en la base de datos asociado a un RUN ingresado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HuespedResponse.class))),
        @ApiResponse(responseCode = "404", description = "Huésped no encontrado")
    })    
    public ResponseEntity<HuespedResponse> buscarHuespedPorRun(@PathVariable String run){
        log.info("GET /api/huespedes/run/{}", run);
        HuespedResponse encontrado = huespedService.buscarHuespedPorRun(run);
        agregarLinks(encontrado);
        return ResponseEntity.ok(encontrado);
    }

    @PostMapping
    @Operation(summary = "Agregar un huésped", description = "Agrega un huésped nuevo en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Huésped agregado", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HuespedResponse.class)))
    })    
    public ResponseEntity<HuespedResponse> agregarHuesped(@Valid @RequestBody HuespedRequest request){
        log.info("POST /api/huespedes -> RUN: {}", request.getRun());
        HuespedResponse agregado = huespedService.agregarHuesped(request);
        agregarLinks(agregado);
        return ResponseEntity.status(HttpStatus.CREATED).body(agregado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un huésped", description = "Actualiza un huésped existente en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Huésped actualizado exitosamente", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = HuespedResponse.class))),
        @ApiResponse(responseCode = "404", description = "Huésped no encontrado")
    })    
    public ResponseEntity<HuespedResponse> actualizarHuesped(@PathVariable Long id, @Valid @RequestBody HuespedUpdateRequest updateRequest){
        log.info("PUT /api/huespedes/{}", id);
        HuespedResponse actualizado = huespedService.actualizarHuesped(id, updateRequest);
        agregarLinks(actualizado);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un huésped", description = "Elimina un huésped existente de la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Huésped eliminado existosamente"),
        @ApiResponse(responseCode = "404", description = "Huésped no encontrado")
    })    
    public ResponseEntity<Void> eliminarHuesped(@PathVariable Long id){
        log.info("GET /api/huespedes/{}", id);
        huespedService.eliminarHuesped(id);
        return ResponseEntity.noContent().build();
    }

    public void agregarLinks(HuespedResponse response){
        response.add(
            linkTo(methodOn(HuespedController.class).buscarHuespedPorId(response.getId())).withSelfRel(),
            linkTo(methodOn(HuespedController.class).buscarHuespedPorRun(response.getRun())).withSelfRel(),
            linkTo(methodOn(HuespedController.class).obtenerHuespedes()).withRel("all"));
    }
}
