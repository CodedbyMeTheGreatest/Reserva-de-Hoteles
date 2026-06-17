package cl.duoc.dsy1103.empleados.service;

import cl.duoc.dsy1103.empleados.client.HotelClient;
import cl.duoc.dsy1103.empleados.client.ReservaClient;
import cl.duoc.dsy1103.empleados.dto.*;
import cl.duoc.dsy1103.empleados.exception.BadRequestException;
import cl.duoc.dsy1103.empleados.mapper.EmpleadoMapper;
import cl.duoc.dsy1103.empleados.model.Empleado;
import cl.duoc.dsy1103.empleados.repository.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Slf4j
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    private final EmpleadoMapper empleadoMapper;

    private final ReservaClient reservaClient;

    private final HotelClient hotelClient;

    EmpleadoService(EmpleadoRepository empleadoRepository, ReservaClient reservaClient, HotelClient hotelClient) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoMapper = new EmpleadoMapper();
        this.reservaClient = reservaClient;
        this.hotelClient = hotelClient;
    }

    public List<EmpleadoResponse> obtenerEmpleados(){
        log.info("Obteniendo todos los empleados...");
        return empleadoRepository.findAll()
                .stream().map(empleadoMapper::toResponse)
                .toList();
    }

    public EmpleadoResponse buscarEmpleadoPorId(Long id){
        log.info("Buscando empleado con ID -> {}", id);
        return empleadoMapper.toResponse(empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el ID "+ id)));
    }

    public EmpleadoResponse buscarEmpleadoPorRun(String run){
        log.info("Buscando empleado con RUN -> {}", run);
        return empleadoMapper.toResponse(empleadoRepository.findByRun(run)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró ningún empleado con el RUN "+ run)));
    }

    public EmpleadoResponse agregarEmpleado(EmpleadoRequest request){
        log.info("Añadiendo empleado con RUN -> {}", request.getRun());
        //Verifica que no exista un empleado con ese RUN
        if(empleadoRepository.existsByRun(request.getRun())){
            throw new BadRequestException("Ya existe un empleado con ese RUN");
        }
        HotelResponse existehotel = hotelClient.buscarHotelPorId(request.getIdHotel());
        Empleado empleado = empleadoMapper.fromRequest(request);
        empleado.setNombreHotel(existehotel.getNombre());
        List<String> cargosValidos = List.of("Recepcionista", "Supervisor Recepcion", "Administrador");
        if(!cargosValidos.contains(request.getCargo())){
            throw new BadRequestException("El cargo ingresado no es valido. Debe ser 'Recepcionista', 'Supervisor Recepcion' o 'Administrador'");
        }
        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
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
            HotelResponse existeHotel = hotelClient.buscarHotelPorId(id);
            empleado.setIdHotel(updateRequest.getIdHotel());
            empleado.setNombreHotel(existeHotel.getNombre());
        }
        return empleadoMapper.toResponse(empleadoRepository.save(empleado));
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
