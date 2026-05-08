package cl.duoc.dsy1103.empleados.controller;

import cl.duoc.dsy1103.empleados.Service.EmpleadoService;
import cl.duoc.dsy1103.empleados.model.Empleado;
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
    public List<Empleado> findAllEmployee(){
        log.info("GET /api/empleados");
        return empleadoService.findAll();
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Empleado> findEmployeeById(@PathVariable Long id){
        log.info("GET /api/empleados/id/{}", id);
        Empleado encontrado = empleadoService.findById(id);
        return ResponseEntity.ok(encontrado);
    }

    @GetMapping("run/{run}")
    public ResponseEntity<Empleado> findEmployeeByRun(@PathVariable String run){
        log.info("GET /api/empleados/run/{}", run);
        Empleado encontrado = empleadoService.findByRun(run);
        return ResponseEntity.ok(encontrado);
    }

    @PostMapping
    public ResponseEntity<Empleado> addEmployee(@Valid @RequestBody Empleado empleado){
        log.info("POST /api/empleados -> run: {}", empleado.getRun());
        Empleado agregado = empleadoService.addEmployee(empleado);
        return ResponseEntity.ok(agregado);
    }

    @DeleteMapping
    public String deleteEmployee(@PathVariable Long id){
        log.info("DELETE /api/empleado -> id: {}", id);
        empleadoService.deleteEmployee(id);
        return "Empleado eliminado correctamente";
    }

}
