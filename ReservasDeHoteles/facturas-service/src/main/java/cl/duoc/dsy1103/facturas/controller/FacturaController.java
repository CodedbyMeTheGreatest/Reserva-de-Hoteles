package cl.duoc.dsy1103.facturas.controller;

import cl.duoc.dsy1103.facturas.dto.FacturaRequest;
import cl.duoc.dsy1103.facturas.dto.FacturaResponse;
import cl.duoc.dsy1103.facturas.dto.FacturaUpdateRequest;
import cl.duoc.dsy1103.facturas.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@Slf4j
public class FacturaController {
    private final FacturaService facturaService;

    FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las facturas", description = "Obtiene todas las facturas existentes de reservas del habitaciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json", 
                        schema = @Schema(implementation = FacturaResponse.class)))
    })
    public ResponseEntity<CollectionModel<FacturaResponse>> obtenerFacturas(){
        log.info("GET /api/facturas");
        List<FacturaResponse> facturas = facturaService.obtenerFacturas();
        facturas.forEach(this::agregarLinks);
        CollectionModel<FacturaResponse> collection = CollectionModel.of(facturas, 
            linkTo(methodOn(FacturaController.class).obtenerFacturas()).withSelfRel());
        return ResponseEntity.ok(collection);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener una factura por una ID específica", description = "Retorna una factura existente en la base de datos asociado a una ID ingresada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = FacturaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })    
    public ResponseEntity<FacturaResponse> buscarFacturaPorId(@PathVariable Long id){
        log.info("GET /api/facturas/{}", id);
        FacturaResponse encontrado = facturaService.buscarFacturaPorId(id);
        agregarLinks(encontrado);
        return ResponseEntity.ok(encontrado);
    }

    @GetMapping("/folio/{folio}")
    @Operation(summary = "Obtener una factura por un folio específico", description = "Retorna una factura existente en la base de datos asociado a un folio ingresado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = FacturaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })       
    public ResponseEntity<FacturaResponse> buscarFacturaPorFolio(@PathVariable String folio){
        log.info("GET /api/facturas/folio/{}", folio);
        FacturaResponse encontrado = facturaService.buscarFacturaPorFolio(folio);
        agregarLinks(encontrado);
        return ResponseEntity.ok(encontrado);
    }

    @PostMapping
    @Operation(summary = "Agregar una factura", description = "Agrega una factura nueva a la base de datos, validando la existencia de todos los microservicios relacionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Factura creada", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = FacturaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Ingreso de datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Datos no encontrados"),
        @ApiResponse(responseCode = "500", description = "Operación fallida")
    })    
    public ResponseEntity<FacturaResponse> agregarFactura(@Valid @RequestBody FacturaRequest request){
        log.info("POST /api/facturas -> FOLIO: {}", request.getFolio());
        FacturaResponse agregado = facturaService.agregarFactura(request);
        agregarLinks(agregado);
        return ResponseEntity.status(HttpStatus.CREATED).body(agregado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una factura", description = "Actualiza una factura existente en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura actualizado exitosamente", 
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = FacturaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos no encontrados"),
        @ApiResponse(responseCode = "404", description = "Ingreso de datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Operación fallida")
    })    
    public ResponseEntity<FacturaResponse> actualizarFactura(@PathVariable Long id, @Valid @RequestBody FacturaUpdateRequest updateRequest){
        log.info("PUT /api/facturas/{}", id);
        FacturaResponse actualizado = facturaService.actualizarFactura(id, updateRequest);
        agregarLinks(actualizado);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una factura", description = "Elimina una factura existente de la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Factura eliminada existosamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })    
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id){
        log.info("DELETE /api/facturas/{}", id);
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }

    public void agregarLinks(FacturaResponse response){
        response.add(
            linkTo(methodOn(FacturaController.class).buscarFacturaPorId(response.getId())).withSelfRel(),
            linkTo(methodOn(FacturaController.class).buscarFacturaPorFolio(response.getFolio())).withSelfRel(),
            linkTo(methodOn(FacturaController.class).obtenerFacturas()).withRel("all")
        );
    }
}
