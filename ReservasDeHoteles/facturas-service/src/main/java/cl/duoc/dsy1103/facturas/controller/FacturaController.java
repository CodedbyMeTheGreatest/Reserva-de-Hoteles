package cl.duoc.dsy1103.facturas.controller;

import cl.duoc.dsy1103.facturas.dto.FacturaRequest;
import cl.duoc.dsy1103.facturas.dto.FacturaResponse;
import cl.duoc.dsy1103.facturas.dto.FacturaUpdateRequest;
import cl.duoc.dsy1103.facturas.service.FacturaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<FacturaResponse>> findAll(){
        log.info("GET /api/facturas");
        return ResponseEntity.ok(facturaService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> findById(@PathVariable Long id){
        log.info("GET /api/facturas/{}", id);
        return ResponseEntity.ok(facturaService.findById(id));
    }

    @GetMapping("/folio/{folio}")
    public ResponseEntity<FacturaResponse> findByFolio(@PathVariable String folio){
        log.info("GET /api/facturas/folio/{}", folio);
        return ResponseEntity.ok(facturaService.findByFolio(folio));
    }

    @PostMapping
    public ResponseEntity<FacturaResponse> addFactura(@Valid @RequestBody FacturaRequest request){
        log.info("POST /api/facturas");
        return ResponseEntity.ok(facturaService.addFactura(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<FacturaResponse> updateFactura(@PathVariable Long id, @Valid @RequestBody FacturaUpdateRequest updateRequest){
        log.info("PUT /api/facturas/{}", id);
        return ResponseEntity.ok(facturaService.updateFactura(id, updateRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFactura(Long id){
        log.info("DELETE /api/facturas/{}", id);
        facturaService.deleteFactura(id);
        return ResponseEntity.noContent().build();
    }
}
