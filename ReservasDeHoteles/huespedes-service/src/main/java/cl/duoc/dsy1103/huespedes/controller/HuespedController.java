package cl.duoc.dsy1103.huespedes.controller;

import cl.duoc.dsy1103.huespedes.dto.HuespedRequest;
import cl.duoc.dsy1103.huespedes.dto.HuespedResponse;
import cl.duoc.dsy1103.huespedes.dto.HuespedUpdateRequest;
import cl.duoc.dsy1103.huespedes.mapper.HuespedMapper;
import cl.duoc.dsy1103.huespedes.service.HuespedService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/huespedes")
@Slf4j
public class HuespedController {
    @Autowired
    private HuespedService huespedService;

    @Autowired
    private HuespedMapper huespedMapper;

    @GetMapping
    public ResponseEntity<List<HuespedResponse>> obtenerHuespedes(){
        log.info("GET /api/huespedes");
        return ResponseEntity.ok(huespedService.obtenerHuespedes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HuespedResponse> buscarHuespedPorId(@PathVariable Long id){
        log.info("GET /api/huespedes/{}", id);
        return ResponseEntity.ok(huespedService.buscarHuespedPorId(id));
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<HuespedResponse> buscarHuespedPorRun(@PathVariable String run){
        log.info("GET /api/huespedes/run/{}", run);
        return ResponseEntity.ok(huespedService.buscarHuespedPorRun(run));
    }

    @PostMapping
    public ResponseEntity<HuespedResponse> agregarHuesped(@Valid @RequestBody HuespedRequest request){
        log.info("POST /api/huespedes -> RUN: {}", request.getRun());
        return ResponseEntity.status(HttpStatus.CREATED).body(huespedService.agregarHuesped(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HuespedResponse> actualizarHuesped(@PathVariable Long id, @Valid @RequestBody HuespedUpdateRequest updateRequest){
        log.info("PUT /api/huespedes/{}", id);
        return ResponseEntity.ok(huespedService.actualizarHuesped(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHuesped(@PathVariable Long id){
        log.info("GET /api/huespedes/{}", id);
        huespedService.eliminarHuesped(id);
        return ResponseEntity.noContent().build();
    }
}
