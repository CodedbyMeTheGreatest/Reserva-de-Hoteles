package cl.duoc.dsy1103.check_in.service;

import cl.duoc.dsy1103.check_in.client.EmpleadoClient;
import cl.duoc.dsy1103.check_in.client.ReservaClient;
import cl.duoc.dsy1103.check_in.dto.CheckInRequest;
import cl.duoc.dsy1103.check_in.dto.CheckInResponse;
import cl.duoc.dsy1103.check_in.dto.EmpleadoResponse;
import cl.duoc.dsy1103.check_in.dto.ReservaResponse;
import cl.duoc.dsy1103.check_in.mapper.CheckInMapper;
import cl.duoc.dsy1103.check_in.model.CheckIn;
import cl.duoc.dsy1103.check_in.repository.CheckInRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<CheckInResponse> findAll(){
        log.info("Obteniendo check-ins ...");
        return checkInRepository.findAll()
                .stream()
                .map(checkInMapper::toResponse)
                .toList();
    }

    public List<CheckInResponse> findAllByIdEmpleado(Long id){
        log.info("Obteniendo check-ins supervisados por empleado con ID -> {}", id);
        EmpleadoResponse existe = empleadoClient.findEmployeeById(id);
        return checkInRepository.findAllByIdEmpleado(id)
                .stream()
                .map(checkInMapper::toResponse)
                .toList();
    }

    public CheckInResponse findById(Long id){
        log.info("Obteniendo check-in con ID -> {}", id);
        return checkInMapper.toResponse(checkInRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se ha encontrado check-in con ID "+ id)));
    }

    public CheckInResponse findByIdReserva(Long id){
        log.info("Obteniendo check-in de reserva con ID -> {}", id);
        return checkInMapper.toResponse(checkInRepository.findByIdReserva(id).orElseThrow(() -> new EntityNotFoundException("No se ha encontrado check-in de reserva con ID "+ id)));
    }

    public CheckInResponse addCheckIn(CheckInRequest request){
        log.info("Agregando check-in para reserva con ID -> {}", request.getIdReserva());
        ReservaResponse existeReserva = reservaClient.findReservaById(request.getIdReserva());
        EmpleadoResponse existeEmpleado = empleadoClient.findEmployeeById(request.getIdEmpleado());
        return checkInMapper.toResponse(checkInRepository.save(checkInMapper.fromRequest(request)));
    }

    public CheckInResponse updateCheckIn(Long id, CheckInRequest request){
        log.info("Actualizando check-in con ID -> {}", id);

        CheckIn checkIn = checkInRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se ha encontrado check-in con ID "+ id));
        checkIn.setId(id);
        checkIn.setFechaIngreso(request.getFechaIngreso());

        ReservaResponse existeReserva = reservaClient.findReservaById(request.getIdReserva());
        checkIn.setIdReserva(request.getIdReserva());

        EmpleadoResponse existeEmpleado = empleadoClient.findEmployeeById(request.getIdEmpleado());
        checkIn.setIdEmpleado(request.getIdEmpleado());
        checkIn.setObservaciones(request.getObservaciones());
        return checkInMapper.toResponse(checkIn);
    }

    public void deleteCheckIn(Long id){
        log.info("Eliminando check-in con ID -> {}", id);
        if (!checkInRepository.existsById(id)){
            throw new EntityNotFoundException("No se ha encontrado check-in con ID " + id);
        }
        checkInRepository.deleteById(id);
    }

}
