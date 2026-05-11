package cl.duoc.dsy1103.empleados.service;

import cl.duoc.dsy1103.empleados.client.HotelClient;
import cl.duoc.dsy1103.empleados.client.ReservaClient;
import cl.duoc.dsy1103.empleados.dto.*;
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

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private HotelClient hotelClient;

    /**
     * Busca todos los empleados
     * @return : Lista de empleados encontrados
     */
    public List<EmpleadoResponse> obtenerEmpleados(){
        log.info("Obteniendo todos los empleados...");
        //Resuelve cmo mostrar los nombres de los hoteles :)
        return empleadoRepository.findAll().stream().map(empleadoMapper::toResponse).toList();
    }

    /**
     * Busca el empleado con el ID entregado
     * @param id Long
     * @return : Respuesta con los datos del empleado encontrado
     */
    public EmpleadoResponse buscarEmpleadoPorId(Long id){
        log.info("Buscando empleado con ID: {}", id);
        Empleado encontrado = empleadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el ID "+ id));
        HotelResponse existehotel = hotelClient.findHotelById(encontrado.getIdHotel());
        EmpleadoResponse empleadoResponse = empleadoMapper.toResponse(encontrado);
        empleadoResponse.setNombreHotel(existehotel.getNombre());
        return empleadoResponse;
    }

    /**
     * Busca el empleado con el RUN entregado
     * @param run String
     * @return : Respuesta con los datos del empleado buscado
     */
    public EmpleadoResponse buscarEmpleadoPorRut(String run){
        log.info("Buscando empleado con RUN: {}", run);
        //Resuelve cmo mostrar los nombres de los hoteles :)
        return empleadoMapper.toResponse(empleadoRepository.findByRun(run).orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el RUN "+ run)));
    }

    /**
     * Añade un empleado
     * @param request EmpleadoRequest
     * @return : Respuesta con los datos del empleado agregado
     */
    public EmpleadoResponse agregarEmpleado(EmpleadoRequest request){
        log.info("Añadiendo empleado con RUN: {}", request.getRun());
        HotelResponse existehotel = hotelClient.findHotelById(request.getIdHotel());
        Empleado empleado = empleadoRepository.save(empleadoMapper.fromRequest(request));
        EmpleadoResponse empleadoResponse = empleadoMapper.toResponse(empleado);
        empleadoResponse.setNombreHotel(existehotel.getNombre());
        return empleadoResponse;
    }

    /**
     * Actualiza los datos de un empleado existente, mediante la entrega de su ID
     * @param id Long - atributo entregado por medio del PathVariable, correspondiente al valor otorgado por el programa al crear el empleado.
     * @param updateRequest - cuerpo Json entregado por medio del RequestBody, correspondiente a todos los datos a actualizar del empleado.
     * @return Respuesta con los datos del empleado actualizados
     */
    public EmpleadoResponse actualizarEmpleado(Long id, EmpleadoUpdateRequest updateRequest){
        log.info("Actualizando empleado con id: {}", id);
        Empleado empleado = empleadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el ID "+ id));

        if(updateRequest.getRun() != null){
            empleado.setRun(updateRequest.getRun());
        }
        if(updateRequest.getNombreCompleto() != null) {
            empleado.setNombreCompleto(updateRequest.getNombreCompleto());
        }
        if(updateRequest.getCargo() != null) {
            empleado.setCargo(updateRequest.getCargo());
        }
        HotelResponse existeHotel = hotelClient.findHotelById(id);
        if(updateRequest.getIdHotel() != null) {
            empleado.setIdHotel(updateRequest.getIdHotel());
        }
        Empleado guardado = empleadoRepository.save(empleado);
        EmpleadoResponse empleadoResponse = empleadoMapper.toResponse(guardado);
        empleadoResponse.setNombreHotel(existeHotel.getNombre());
        return empleadoResponse;
    }

    /**
     * Elimina un empleado con el ID entregado
     * @param id Long
     */
    public void eliminarEmpleado(Long id){
        log.info("Eliminando empleado con ID: {}", id);
        if(!empleadoRepository.existsById(id)){
            throw new EntityNotFoundException("No se encontró ningún empleado con la ID "+id);
        }
        empleadoRepository.deleteById(id);
    }

    public List<ReservaResponse> obtenerEmpleadosPorIdReserva(String run){
        log.info("Obteniendo reservas por el empleado con RUN -> {}", run);
        if(!empleadoRepository.existsByRun(run)){
            throw new EntityNotFoundException("No se encontró ningún empleado con el RUN "+ run);
        }
        //Resuelve cmo mostrar los nombres de los hoteles :)
        return reservaClient.findReservaByEmployeeRun(run);
    }

}
