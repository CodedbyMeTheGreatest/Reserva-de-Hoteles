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

    //@Autowired
    //private HotelClient hotelClient;

    public List<EmpleadoResponse> obtenerEmpleados(){
        log.info("Obteniendo todos los empleados...");
        return empleadoRepository.findAll()
                .stream().map(empleadoMapper::toResponse)
                .toList();
    }

    public EmpleadoResponse buscarEmpleadoPorId(Long id){
        log.info("Buscando empleado con ID -> {}", id);
        Empleado encontrado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el ID "+ id));
        return empleadoMapper.toResponse(encontrado);
    }

    public EmpleadoResponse buscarEmpleadoPorRut(String run){
        log.info("Buscando empleado con RUN -> {}", run);
        Empleado encontrado = empleadoRepository.findByRun(run)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el RUN "+ run));
        return empleadoMapper.toResponse(encontrado);
    }

    public EmpleadoResponse agregarEmpleado(EmpleadoRequest request){
        log.info("Añadiendo empleado con RUN -> {}", request.getRun());
        //HotelResponse existehotel = hotelClient.findHotelById(request.getIdHotel());
        Empleado empleado = empleadoMapper.fromRequest(request);
        //empleado.setNombreHotel(existehotel.getNombre());
        Empleado agregado = empleadoRepository.save(empleado);
        return empleadoMapper.toResponse(agregado);
    }

    public EmpleadoResponse actualizarEmpleado(Long id, EmpleadoUpdateRequest updateRequest){
        log.info("Actualizando empleado con id -> {}", id);
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el ID "+ id));

        if(updateRequest.getRun() != null){
            empleado.setRun(updateRequest.getRun());
        }
        if(updateRequest.getNombreCompleto() != null) {
            empleado.setNombreCompleto(updateRequest.getNombreCompleto());
        }
        if(updateRequest.getCargo() != null) {
            empleado.setCargo(updateRequest.getCargo());
        }
        if(updateRequest.getIdHotel() != null) {
            //HotelResponse existeHotel = hotelClient.findHotelById(id);
            empleado.setIdHotel(updateRequest.getIdHotel());
            //empleado.setNombreHotel(existeHotel.getNombre());
        }
        Empleado actualizado = empleadoRepository.save(empleado);
        return empleadoMapper.toResponse(actualizado);
    }

    public void eliminarEmpleado(Long id){
        log.info("Eliminando empleado con ID -> {}", id);
        if(!empleadoRepository.existsById(id)){
            throw new EntityNotFoundException("No se encontró ningún empleado para eliminar con la ID "+id);
        }
        empleadoRepository.deleteById(id);
    }

    public List<ReservaResponse> obtenerReservasPorRunEmpleado(String run){
        log.info("Obteniendo reservas por el empleado con RUN -> {}", run);
        if(!empleadoRepository.existsByRun(run)){
            throw new EntityNotFoundException("No se encontró ningún empleado con el RUN "+ run);
        }
        return reservaClient.obtenerReservasPorRunEmpleado(run);
    }

}
