package cl.duoc.dsy1103.empleados.service;

import cl.duoc.dsy1103.empleados.dto.EmpleadoRequest;
import cl.duoc.dsy1103.empleados.dto.EmpleadoResponse;
import cl.duoc.dsy1103.empleados.mapper.EmpleadoMapper;
import cl.duoc.dsy1103.empleados.model.Empleado;
import cl.duoc.dsy1103.empleados.repository.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    /**
     * Busca todos los empleados
     * @return : Lista de empleados
     */
    public List<EmpleadoResponse> findAll(){
        log.info("Obteniendo todos los empleados...");
        return empleadoRepository.findAll().stream().map(empleadoMapper::toResponse).toList();
    }

    /**
     * Busca el empleado con la ID entregada
     * @param id Long
     * @return : Respuesta con los datos del empleado encontrado
     */
    public EmpleadoResponse findById(Long id){
        log.info("Buscando empleado con ID: {}", id);
        return empleadoMapper.toResponse(empleadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el ID "+ id)));
    }

    /**
     * Busca el empleado con el RUN entregado
     * @param run String
     * @return
     */
    public EmpleadoResponse findByRun(String run){
        log.info("Buscando empleado con RUN: {}", run);
        return empleadoMapper.toResponse(empleadoRepository.findByRun(run).orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el RUN "+ run)));
    }

    /**
     * Añade un empleado
     * @param: empleado EmpleadoRequest
     * @return
     */
    public EmpleadoResponse addEmployee(EmpleadoRequest request){
        log.info("Añadiendo empleado con RUN: {}", request.getRun());
        Empleado empleado = empleadoRepository.save(empleadoMapper.fromRequest(request));
        return empleadoMapper.toResponse(empleado);
    }

    /**
     * Actualiza los datos de un empleado existente, mediante la entrega de su ID
     * @param id Long - atributo entregado por medio del PathVariable, correspondiente al valor otorgado por el programa al crear el empleado.
     * @param request - cuerpo Json entregado por medio del RequestBody, correspondiente a todos los datos a actualizar del empleado.
     * @return EmpleadoResponse
     */
    public EmpleadoResponse updateEmployee(Long id, EmpleadoRequest request){
        log.info("Actualizando empleado con id: {}", id);
        Empleado existente = empleadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el ID "+ id));

        existente.setRun(request.getRun());
        existente.setNombreCompleto(request.getNombreCompleto());
        existente.setCargo(request.getCargo());
        existente.setIdHotel(request.getIdHotel());

        Empleado actualizado = empleadoRepository.save(existente);
        return empleadoMapper.toResponse(actualizado);
    }

    /**
     * Elimina un empleado con el ID entregado
     * @param id Long
     */
    public void deleteEmployee(Long id){
        log.info("Eliminando empleado con ID: {}", id);
        if(!empleadoRepository.existsById(id)){
            throw new EntityNotFoundException("No se encontró ningún empleado con la ID "+id);
        }
        empleadoRepository.deleteById(id);
    }

}
