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
@RequestMapping("/api/check-ins")
@Slf4j
public class CheckInController {
    @Autowired
    private CheckInService checkInService;

    //find all
    @GetMapping
    public ResponseEntity<List<CheckInResponse>> findAll(){
        log.info("GET /api/check-ins");
        return ResponseEntity.ok(checkInService.findAll());
    }

    //find by id employee
    @GetMapping("/empleado/{id}")
    public ResponseEntity<List<CheckInResponse>> findAllByEmployeeId(@PathVariable Long id){
        log.info("GET /api/check-ins/empleado/{}", id);
        return ResponseEntity.ok(checkInService.findAllByIdEmpleado(id));
    }

    //find by id
    @GetMapping("{id}")
    public ResponseEntity<CheckInResponse> findById(@PathVariable Long id){
        log.info("GET /api/check-ins/{}", id);
        return ResponseEntity.ok(checkInService.findById(id));
    }

    //find by id reserva
    @GetMapping("/id-reserva/{id}")
    public ResponseEntity<CheckInResponse> findByIdReserva(@PathVariable Long id){
        log.info("GET /api/check-ins/id-reserva/{}", id);
        return ResponseEntity.ok(checkInService.findByIdReserva(id));
    }

    //add
    @PostMapping
    public ResponseEntity<CheckInResponse> add(@Valid @RequestBody CheckInRequest request){
        log.info("GET /api/check-ins -> {}", request.getIdReserva());
        return ResponseEntity.ok(checkInService.addCheckIn(request));
    }

    //update
    @PutMapping("{id}")
    public ResponseEntity<CheckInResponse> update(@PathVariable Long id, @Valid @RequestBody CheckInUpdateRequest updateRequest){
        log.info("PUT /api/check-ins/{}", id);
        return ResponseEntity.ok(checkInService.updateCheckIn(id, updateRequest));
    }

    //delete
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(Long id){
        log.info("DELETE /api/check-ins/{}", id);
        checkInService.deleteCheckIn(id);
        return ResponseEntity.noContent().build();
    }
}
