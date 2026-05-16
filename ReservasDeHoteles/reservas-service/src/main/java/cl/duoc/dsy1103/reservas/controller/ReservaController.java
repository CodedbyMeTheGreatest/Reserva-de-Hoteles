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
    public ResponseEntity<List<ReservaResponse>> buscarReservas(){
        log.info("GET /api/reservas/buscarReservas");
        return ResponseEntity.ok().body(reservaService.buscarReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> buscarReservaPorId(@PathVariable("id") Long id){
        log.info("GET /api/reservas/{}", id);
        return ResponseEntity.ok().body(reservaService.buscarReservaPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> crearReserva (@Valid @RequestBody ReservaRequest request) {
        log.info("POST /api/reservas/crearReserva");
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crearReserva(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> actualizarReserva (@PathVariable("id") Long idReserva, @Valid @RequestBody ReservaUpdateRequest request){
        log.info("PUT /api/reservas/actualizarReserva/{}", idReserva);
        return ResponseEntity.ok().body(reservaService.actualizarReserva(idReserva, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva (@PathVariable("id") Long idReserva){
        log.info("DELETE /api/reservas/eliminarReserva/{}", idReserva);
        reservaService.eliminarReserva(idReserva);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empleado/{run}")
    public ResponseEntity<List<ReservaResponse>> buscarReservasPorEmpleado(@PathVariable("run") String run){
        log.info("GET /api/reservas/empleado/{}", run);
        return ResponseEntity.ok().body(reservaService.buscarReservasPorEmpleado(run));
    }
}
