package cl.duoc.dsy1103.check_in.service;

import cl.duoc.dsy1103.check_in.client.EmpleadoClient;
import cl.duoc.dsy1103.check_in.client.ReservaClient;
import cl.duoc.dsy1103.check_in.dto.*;
import cl.duoc.dsy1103.check_in.mapper.CheckInMapper;
import cl.duoc.dsy1103.check_in.model.CheckIn;
import cl.duoc.dsy1103.check_in.repository.CheckInRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CheckInService {
    @Autowired
    private CheckInRepository checkInRepository;
    @Autowired
    private CheckInMapper checkInMapper;

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private EmpleadoClient empleadoClient;

    public List<CheckInResponse> obtenerCheckIns(){
        log.info("Obteniendo check-ins ...");
        return checkInRepository.findAll()
                .stream()
                .map(checkInMapper::toResponse)
                .toList();
    }

    
    public CheckInResponse buscarCheckInPorId(Long id){
        log.info("Obteniendo check-in con ID -> {}", id);
        return checkInMapper.toResponse(checkInRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado check-in con ID ->"+ id)));
    }
    
    public CheckInResponse buscarCheckInPorIdReserva(Long id){
        log.info("Obteniendo check-in de reserva con ID -> {}", id);
        return checkInMapper.toResponse(checkInRepository.findByIdReserva(id)
        .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado el check-in de la reserva con ID -> "+ id)));
    }
    
    public CheckInResponse agregarCheckIn(CheckInRequest request){
        log.info("Agregando check-in para reserva con ID -> {}", request.getIdReserva());
        //ReservaResponse existeReserva = reservaClient.buscarReservaPorId(request.getIdReserva());
        if(checkInRepository.existsByIdReserva(request.getIdReserva())){
            throw new DataIntegrityViolationException("Ya existe un check-in para la reserva con ID -> "+ request.getIdReserva());
        }
        EmpleadoResponse existeEmpleado = empleadoClient.buscarEmpleadoPorId(request.getIdEmpleado());
        CheckIn agregado = checkInRepository.save(checkInMapper.fromRequest(request));
        return checkInMapper.toResponse(agregado);
    }
    
    public CheckInResponse actualizarCheckIn(Long id, CheckInUpdateRequest updateRequest){
        log.info("Actualizando check-in con ID -> {}", id);
        CheckIn checkIn = checkInRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se ha encontrado check-in con ID ->"+ id));
        if(updateRequest.getFechaIngreso() != null) {
            checkIn.setFechaIngreso(updateRequest.getFechaIngreso());
        }
        
        if (updateRequest.getIdReserva() != null) {
            //ReservaResponse existeReserva = reservaClient.buscarReservaPorId(updateRequest.getIdReserva());
            checkIn.setIdReserva(updateRequest.getIdReserva());
        }
        
        if(updateRequest.getIdEmpleado() != null) {
            EmpleadoResponse existeEmpleado = empleadoClient.buscarEmpleadoPorId(updateRequest.getIdEmpleado());
            checkIn.setIdEmpleado(updateRequest.getIdEmpleado());
        }
        if(updateRequest.getObservaciones() != null){
            checkIn.setObservaciones(updateRequest.getObservaciones());
        }
        CheckIn actualizado = checkInRepository.save(checkIn);
        return checkInMapper.toResponse(actualizado);
    }
    
    public void eliminarCheckIn(Long id){
        log.info("Eliminando check-in con ID -> {}", id);
        if (!checkInRepository.existsById(id)){
            throw new EntityNotFoundException("No se ha encontrado check-in con ID ->" + id);
        }
        checkInRepository.deleteById(id);
    }
    
    public List<CheckInResponse> obtenerCheckInsPorIdEmpleado(Long id){
        log.info("Obteniendo check-ins supervisados por empleado con ID -> {}", id);
        EmpleadoResponse existe = empleadoClient.buscarEmpleadoPorId(id);
        return checkInRepository.findAllByIdEmpleado(id)
                .stream()
                .map(checkInMapper::toResponse)
                .toList();
    }
}
