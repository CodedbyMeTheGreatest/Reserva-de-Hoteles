package cl.duoc.dsy1103.pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import cl.duoc.dsy1103.pagos.dto.PagoRequest;
import cl.duoc.dsy1103.pagos.dto.PagoResponse;
import cl.duoc.dsy1103.pagos.dto.PagoUpdateRequest;
import cl.duoc.dsy1103.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponse>> buscarPagos(){
        log.info("GET /api/pagos");
        return ResponseEntity.ok().body(pagoService.buscarPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> buscarPagoPorId(@PathVariable("id") Long id){
        log.info("GET /api/pagos/{}", id);
        return ResponseEntity.ok().body(pagoService.buscarPagoPorId(id));
    }

    @PostMapping
    public ResponseEntity<PagoResponse> crearPago (@Valid @RequestBody PagoRequest request) {
        log.info("POST /api/pagos/crearPago");
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.crearPago(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponse> actualizarPago (@PathVariable("id") Long idPago, @Valid @RequestBody PagoUpdateRequest request){
        log.info("PUT /api/pagos/actualizarPago/{}", idPago);
        return ResponseEntity.ok().body(pagoService.actualizarPago(idPago, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago (@PathVariable("id") Long idPago){
        log.info("DELETE /api/pagos/eliminarPago/{}", idPago);
        pagoService.eliminarPago(idPago);
        return ResponseEntity.noContent().build();
    }
}
