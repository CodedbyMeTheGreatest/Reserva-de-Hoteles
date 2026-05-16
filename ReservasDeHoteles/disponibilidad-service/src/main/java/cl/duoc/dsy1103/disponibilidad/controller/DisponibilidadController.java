package cl.duoc.dsy1103.disponibilidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadRequest;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadResponse;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadUpdateRequest;
import cl.duoc.dsy1103.disponibilidad.service.DisponibilidadService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@Slf4j
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @GetMapping
    public ResponseEntity<List<DisponibilidadResponse>> buscarDisponibilidades(){
        log.info("GET /api/disponibilidades");
        return ResponseEntity.ok().body(disponibilidadService.buscarDisponibilidades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadResponse> buscarDisponibilidadPorId(@PathVariable("id")Long idDisponibilidad){
        log.info("GET /api/disponibilidades/{}", idDisponibilidad);
        return ResponseEntity.ok().body(disponibilidadService.buscarDisponibilidadPorId(idDisponibilidad));
    }

    @PostMapping
    public ResponseEntity<DisponibilidadResponse> crearDisponibilidad (@Valid @RequestBody DisponibilidadRequest request) {
        log.info("POST /api/disponibilidades/crearDisponibilidad");
        return ResponseEntity.status(HttpStatus.CREATED).body(disponibilidadService.crearDisponibilidad(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadResponse> actualizarDisponibilidad (@PathVariable("id") Long idDisponibilidad, @Valid @RequestBody DisponibilidadUpdateRequest request){
        log.info("PUT /api/disponibilidades/actualizarDisponibilidad/{}", idDisponibilidad);
        return ResponseEntity.ok().body(disponibilidadService.actualizarDisponibilidad(idDisponibilidad, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDisponibilidad (@PathVariable("id") Long idDisponibilidad){
        log.info("DELETE /api/disponibilidades/eliminarDisponibilidad/{}",idDisponibilidad);
        disponibilidadService.eliminarDisponibilidad(idDisponibilidad);
        return ResponseEntity.noContent().build();
    }

}
