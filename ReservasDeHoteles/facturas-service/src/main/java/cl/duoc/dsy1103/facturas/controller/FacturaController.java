package cl.duoc.dsy1103.facturas.controller;

import cl.duoc.dsy1103.facturas.dto.FacturaRequest;
import cl.duoc.dsy1103.facturas.dto.FacturaResponse;
import cl.duoc.dsy1103.facturas.dto.FacturaUpdateRequest;
import cl.duoc.dsy1103.facturas.service.FacturaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@Slf4j
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<FacturaResponse>> obtenerFacturas(){
        log.info("GET /api/facturas");
        return ResponseEntity.ok(facturaService.obtenerFacturas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> buscarFacturaPorId(@PathVariable Long id){
        log.info("GET /api/facturas/{}", id);
        return ResponseEntity.ok(facturaService.buscarFacturaPorId(id));
    }

    @GetMapping("/folio/{folio}")
    public ResponseEntity<FacturaResponse> buscarFacturaPorFolio(@PathVariable String folio){
        log.info("GET /api/facturas/folio/{}", folio);
        return ResponseEntity.ok(facturaService.buscarFacturaPorFolio(folio));
    }

    @PostMapping
    public ResponseEntity<FacturaResponse> agregarFactura(@Valid @RequestBody FacturaRequest request){
        log.info("POST /api/facturas -> FOLIO: {}", request.getFolio());
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.agregarFactura(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaResponse> actualizarFactura(@PathVariable Long id, @Valid @RequestBody FacturaUpdateRequest updateRequest){
        log.info("PUT /api/facturas/{}", id);
        return ResponseEntity.ok(facturaService.actualizarFactura(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id){
        log.info("DELETE /api/facturas/{}", id);
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}
