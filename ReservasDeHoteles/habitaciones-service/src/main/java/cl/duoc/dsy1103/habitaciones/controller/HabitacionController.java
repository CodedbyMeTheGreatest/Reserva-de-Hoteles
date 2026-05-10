package cl.duoc.dsy1103.habitaciones.controller;

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

import cl.duoc.dsy1103.habitaciones.dto.HabitacionRequest;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionResponse;
import cl.duoc.dsy1103.habitaciones.dto.HabitacionUpdateRequest;
import cl.duoc.dsy1103.habitaciones.model.Habitacion;
import cl.duoc.dsy1103.habitaciones.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/habitaciones")
@Slf4j
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping
    public List<Habitacion> buscarHabitaciones(){
        log.info("GET /api/habitaciones/buscarHabitaciones");
        return habitacionService.buscarHabitaciones();
    }

    @GetMapping("/{id}")
    public HabitacionResponse buscarHabitacionPorId(@PathVariable Long idHabitacion){
        log.info("GET /api/habitaciones/{id}", idHabitacion);
        return habitacionService.buscarHabitacionPorId(idHabitacion);
    }

    @PostMapping
    public ResponseEntity<HabitacionResponse> crearHabitacion (@Valid @RequestBody HabitacionRequest request) {
        log.info("POST /api/habitaciones/crearHabitacion");
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacionService.crearHabitacion(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitacionResponse> actualizarHabitacion (@PathVariable Long idHabitacion, @Valid @RequestBody HabitacionUpdateRequest request){
        log.info("PUT /api/habitaciones/actualizarHabitacion/{}", idHabitacion);
        return ResponseEntity.ok().body(habitacionService.actualizarHabitacion(idHabitacion, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion (@PathVariable Long idHabitacion){
        log.info("DELETE /api/habitaciones/eliminarHabitacion/{id}",idHabitacion);
        habitacionService.eliminarHabitacion(idHabitacion);
        return ResponseEntity.noContent().build();
    }
    
}
