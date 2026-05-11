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
    public List<EmpleadoResponse> obtenerEmpleados(){
        log.info("GET /api/empleados");
        return empleadoService.obtenerEmpleados();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EmpleadoResponse> buscarEmpleadoPorId(@PathVariable Long id){
        log.info("GET /api/empleados/id/{}", id);
        EmpleadoResponse encontrado = empleadoService.buscarEmpleadoPorId(id);
        return ResponseEntity.ok(encontrado);
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<EmpleadoResponse> buscarEmpleadoPorRut(@PathVariable String run){
        log.info("GET /api/empleados/run/{}", run);
        EmpleadoResponse encontrado = empleadoService.buscarEmpleadoPorRut(run);
        return ResponseEntity.ok(encontrado);
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> agregarEmpleado(@Valid @RequestBody EmpleadoRequest request){
        log.info("POST /api/empleados -> run: {}", request.getRun());
        EmpleadoResponse agregado = empleadoService.agregarEmpleado(request);
        return ResponseEntity.ok(agregado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoUpdateRequest updateRequest){
        log.info("Put /api/empleados -> id: {} run: {}", id,updateRequest.getRun());
        EmpleadoResponse actualizado = empleadoService.actualizarEmpleado(id, updateRequest);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id){
        log.info("DELETE /api/empleado -> id: {}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reservas{run}")
    public ResponseEntity<List<ReservaResponse>> obtenerEmpleadosPorIdReserva(@PathVariable String run){
        log.info("Get /api/empleados/run/reservas/{} ", run);
        return ResponseEntity.ok(empleadoService.obtenerEmpleadosPorIdReserva(run));
    }

}
