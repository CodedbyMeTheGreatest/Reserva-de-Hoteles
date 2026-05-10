package cl.duoc.dsy1103.reservas.controller;

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

import cl.duoc.dsy1103.reservas.dto.ReservaRequest;
import cl.duoc.dsy1103.reservas.dto.ReservaResponse;
import cl.duoc.dsy1103.reservas.dto.ReservaUpdateRequest;
import cl.duoc.dsy1103.reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/reservas")
@Slf4j
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<ReservaResponse> buscarReservas(){
        log.info("GET /api/reservas/buscarReservas");
        return reservaService.buscarReservas();
    }

    @GetMapping("/{id}")
    public ReservaResponse buscarReservaPorId(@PathVariable Long id){
        log.info("GET /api/reservas/{}", id);
        return reservaService.buscarReservaPorId(id);
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> crearReserva (@Valid @RequestBody ReservaRequest request) {
        log.info("POST /api/reservas/crearReserva");
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crearReserva(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> actualizarReserva (@PathVariable Long idReserva, @Valid @RequestBody ReservaUpdateRequest request){
        log.info("PUT /api/reservas/actualizarReserva/{}", idReserva);
        return ResponseEntity.ok().body(reservaService.actualizarReserva(idReserva, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva (@PathVariable Long idReserva){
        log.info("DELETE /api/reservas/eliminarReserva/{}", idReserva);
        reservaService.eliminarReserva(idReserva);
        return ResponseEntity.noContent().build();
    }
}
