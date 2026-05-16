package cl.duoc.dsy1103.empleados.controller;

import cl.duoc.dsy1103.empleados.dto.EmpleadoRequest;
import cl.duoc.dsy1103.empleados.dto.EmpleadoResponse;
import cl.duoc.dsy1103.empleados.dto.EmpleadoUpdateRequest;
import cl.duoc.dsy1103.empleados.dto.ReservaResponse;
import cl.duoc.dsy1103.empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<EmpleadoResponse> obtenerEmpleados(){
        log.info("GET /api/empleados");
        return empleadoService.obtenerEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> buscarEmpleadoPorId(@PathVariable Long id){
        log.info("GET /api/empleados/id/{}", id);
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<EmpleadoResponse> buscarEmpleadoPorRut(@PathVariable String run){
        log.info("GET /api/empleados/run/{}", run);
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorRut(run));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> agregarEmpleado(@Valid @RequestBody EmpleadoRequest request){
        log.info("POST /api/empleados -> RUN: {}", request.getRun());
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.agregarEmpleado(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoUpdateRequest updateRequest){
        log.info("PUT /api/empleados/{} -> RUN: {}", id,updateRequest.getRun());
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id){
        log.info("DELETE /api/empleado/{}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservas/{run}")
    public ResponseEntity<List<ReservaResponse>> obtenerReservasPorRunEmpleado(@PathVariable String run){
        log.info("GET /api/empleados/run/reservas/{} ", run);
        return ResponseEntity.ok(empleadoService.obtenerReservasPorRunEmpleado(run));
    }

}
