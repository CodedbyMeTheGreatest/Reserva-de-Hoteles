package cl.duoc.dsy1103.empleados.Service;


import cl.duoc.dsy1103.empleados.model.Empleado;
import cl.duoc.dsy1103.empleados.repository.EmpleadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Busca todos los empleados
     * @return
     */
    public List<Empleado> findAll(){
        log.info("Obteniendo todos los empleados...");
        return empleadoRepository.findAll();
    }

    /**
     * Busca el empleado con la ID entregada
     * @param id
     * @return
     */
    public Empleado findById(Long id){
        log.info("Buscando empleado con ID: {}", id);
        // si no existe nadie con esa id, throw new
        return empleadoRepository.findById(id).get();
    }

    /**
     * Busca el empleado con el RUN entregado
     * @param run
     * @return
     */
    public Empleado findByRun(String run){
        log.info("Buscando empleado con RUN: {}", run);
        // si no existe nadie con ese run, throw new
        return empleadoRepository.findByRun(run);
    }

    /**
     * Añade un empleado
     * @param empleado
     * @return
     */
    public Empleado addEmployee(Empleado empleado){
        log.info("Añadiendo empleado con ID: {}", empleado.getIdEmpleado());
        return empleadoRepository.save(empleado);
    }

    /**
     * Elimina un empleado con la ID entregada
     * @param id
     */
    public void deleteEmployee(Long id){
        log.info("Eliminando empleado con ID: {}", id);
        empleadoRepository.deleteById(id);

    }

}
