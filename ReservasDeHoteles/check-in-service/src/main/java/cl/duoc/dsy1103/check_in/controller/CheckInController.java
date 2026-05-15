package cl.duoc.dsy1103.check_in.controller;

import cl.duoc.dsy1103.check_in.dto.CheckInRequest;
import cl.duoc.dsy1103.check_in.dto.CheckInResponse;
import cl.duoc.dsy1103.check_in.dto.CheckInUpdateRequest;
import cl.duoc.dsy1103.check_in.service.CheckInService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check_in")
@Slf4j
public class CheckInController {
    @Autowired
    private CheckInService checkInService;

    @GetMapping
    public ResponseEntity<List<CheckInResponse>> obtenerCheckIns(){
        log.info("GET /api/check_in");
        return ResponseEntity.ok(checkInService.obtenerCheckIns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckInResponse> buscarCheckInPorId(@PathVariable Long id){
        log.info("GET /api/check_in/{}", id);
        return ResponseEntity.ok(checkInService.buscarCheckInPorId(id));
    }

    @PostMapping
    public ResponseEntity<CheckInResponse> agregarCheckIn(@Valid @RequestBody CheckInRequest request){
        log.info("GET /api/check_in -> {}", request.getIdReserva());
        return ResponseEntity.ok(checkInService.agregarCheckIn(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckInResponse> actualizarCheckIn(@PathVariable Long id, @Valid @RequestBody CheckInUpdateRequest updateRequest){
        log.info("PUT /api/check_in/{}", id);
        return ResponseEntity.ok(checkInService.actualizarCheckIn(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCheckIn(Long id){
        log.info("DELETE /api/check_in/{}", id);
        checkInService.eliminarCheckIn(id);
        return ResponseEntity.noContent().build();
    }
}
