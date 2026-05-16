package cl.duoc.dsy1103.check_out.controller;

import cl.duoc.dsy1103.check_out.dto.CheckOutRequest;
import cl.duoc.dsy1103.check_out.dto.CheckOutResponse;
import cl.duoc.dsy1103.check_out.dto.CheckOutUpdateRequest;
import cl.duoc.dsy1103.check_out.service.CheckOutService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check_out")
@Slf4j
public class CheckOutController {
    @Autowired
    private CheckOutService checkOutService;

    @GetMapping
    public ResponseEntity<List<CheckOutResponse>> obtenerCheckOut(){
        log.info("GET /api/check_out");
        return ResponseEntity.ok(checkOutService.obtenerCheckOut());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckOutResponse> buscarCheckOutPorId(@PathVariable Long id){
        log.info("GET /api/check_out/{}", id);
        return ResponseEntity.ok(checkOutService.buscarCheckOutPorId(id));
    }

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<CheckOutResponse> buscarCheckOutPorIdReserva(@PathVariable Long idReserva){
        log.info("GET /api/check_out/reserva/{}", idReserva);
        return ResponseEntity.ok(checkOutService.buscarCheckOutPorIdReserva(idReserva));
    }

    @PostMapping
    public ResponseEntity<CheckOutResponse> agregarCheckOut(@Valid @RequestBody CheckOutRequest request){
        log.info("GET /api/check_out -> ID: {}", request.getIdReserva());
        return ResponseEntity.status(HttpStatus.CREATED).body(checkOutService.agregarCheckOut(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckOutResponse> actualizarCheckOut(@PathVariable Long id, @Valid @RequestBody CheckOutUpdateRequest updateRequest){
        log.info("PUT /api/check_out/{}", id);
        return ResponseEntity.ok(checkOutService.actualizarCheckOut(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCheckOut(Long id){
        log.info("DELETE /api/check_out/{}", id);
        checkOutService.eliminarCheckOut(id);
        return ResponseEntity.noContent().build();
    }
}
