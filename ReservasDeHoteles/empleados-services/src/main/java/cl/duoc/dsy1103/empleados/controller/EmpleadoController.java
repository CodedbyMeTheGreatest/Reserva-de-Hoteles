package cl.duoc.dsy1103.empleados.controller;

import cl.duoc.dsy1103.empleados.dto.EmpleadoRequest;
import cl.duoc.dsy1103.empleados.dto.EmpleadoResponse;
import cl.duoc.dsy1103.empleados.dto.EmpleadoUpdateRequest;
import cl.duoc.dsy1103.empleados.dto.ReservaResponse;
import cl.duoc.dsy1103.empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@Slf4j
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;


    @GetMapping
    public List<EmpleadoResponse> findAllEmployee(){
        log.info("GET /api/empleados");
        return empleadoService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EmpleadoResponse> findEmployeeById(@PathVariable Long id){
        log.info("GET /api/empleados/id/{}", id);
        EmpleadoResponse encontrado = empleadoService.findById(id);
        return ResponseEntity.ok(encontrado);
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<EmpleadoResponse> findEmployeeByRun(@PathVariable String run){
        log.info("GET /api/empleados/run/{}", run);
        EmpleadoResponse encontrado = empleadoService.findByRun(run);
        return ResponseEntity.ok(encontrado);
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> addEmployee(@Valid @RequestBody EmpleadoRequest request){
        log.info("POST /api/empleados -> run: {}", request.getRun());
        EmpleadoResponse agregado = empleadoService.addEmployee(request);
        return ResponseEntity.ok(agregado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmpleadoUpdateRequest updateRequest){
        log.info("Put /api/empleados -> id: {} run: {}", id,updateRequest.getRun());
        EmpleadoResponse actualizado = empleadoService.updateEmployee(id, updateRequest);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        log.info("DELETE /api/empleado -> id: {}", id);
        empleadoService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservas{run}")
    public ResponseEntity<List<ReservaResponse>> findReservaByEmployeeRun(@PathVariable String run){
        log.info("Get /api/empleados/run/reservas/{} ", run);
        return ResponseEntity.ok(empleadoService.findReservaByEmployeeRun(run));
    }

}
