package cl.duoc.dsy1103.check_out.service;

import cl.duoc.dsy1103.check_out.client.EmpleadoClient;
import cl.duoc.dsy1103.check_out.client.ReservaClient;
import cl.duoc.dsy1103.check_out.dto.*;
import cl.duoc.dsy1103.check_out.exception.BadRequestException;
import cl.duoc.dsy1103.check_out.mapper.CheckOutMapper;
import cl.duoc.dsy1103.check_out.model.CheckOut;
import cl.duoc.dsy1103.check_out.repository.CheckOutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CheckOutService {
    private final CheckOutRepository checkOutRepository;

    private final CheckOutMapper checkOutMapper;

    private final EmpleadoClient empleadoClient;

    private final ReservaClient reservaClient;

    CheckOutService(ReservaClient reservaClient, EmpleadoClient empleadoClient, CheckOutRepository checkOutRepository) {
        this.checkOutMapper = new CheckOutMapper();
        this.reservaClient = reservaClient;
        this.empleadoClient = empleadoClient;
        this.checkOutRepository = checkOutRepository;
    }

    public List<CheckOutResponse> obtenerCheckOut(){
        log.info("Obteniendo check-out...");
        return checkOutRepository.findAll().stream()
                .map(checkOutMapper::toResponse).toList();
    }

    public CheckOutResponse buscarCheckOutPorId(Long id){
        log.info("Buscando check-out con ID -> {}", id);
        return checkOutMapper.toResponse(checkOutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado check-out con ID -> "+ id)));
    }

    public CheckOutResponse buscarCheckOutPorIdReserva(Long idReserva){
        log.info("Buscando check-out de reserva con ID -> {}", idReserva);
        ReservaResponse existe = reservaClient.buscarReservaPorId(idReserva);
        return checkOutMapper.toResponse(checkOutRepository.findByIdReserva(idReserva)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado check-out con ID_RESERVA -> "+idReserva)));
    }

    public CheckOutResponse agregarCheckOut(CheckOutRequest request){
        log.info("Agregando check-out para reserva con ID -> {}", request.getIdReserva());
        ReservaResponse existeReserva = reservaClient.buscarReservaPorId(request.getIdReserva());
        if(checkOutRepository.existsByIdReserva(request.getIdReserva())){
            throw new BadRequestException("Ya existe un check-out para la reserva con ID -> "+ request.getIdReserva());
        }
        EmpleadoResponse existeEmpleado = empleadoClient.buscarEmpleadoPorId(request.getIdEmpleado());
        return checkOutMapper.toResponse(checkOutRepository.save(checkOutMapper.fromRequest(request)));
    }

    public CheckOutResponse actualizarCheckOut(Long id, CheckOutUpdateRequest updateRequest){
        log.info("Actualizando check-out para reserva con ID -> {}", updateRequest.getIdReserva());
        CheckOut checkOut = checkOutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado check-out con ID -> "+id));

        if (updateRequest.getIdReserva() != null) {
            ReservaResponse existeReserva = reservaClient.buscarReservaPorId(updateRequest.getIdReserva());
            checkOut.setIdReserva(updateRequest.getIdReserva());
        }
        if(updateRequest.getIdEmpleado() != null) {
            EmpleadoResponse existeEmpleado = empleadoClient.buscarEmpleadoPorId(updateRequest.getIdEmpleado());
            checkOut.setIdEmpleado(updateRequest.getIdEmpleado());
        }
        if(updateRequest.getObservaciones() != null){
            checkOut.setObservaciones(updateRequest.getObservaciones());
        }
        return checkOutMapper.toResponse(checkOutRepository.save(checkOut));
    }

    public void eliminarCheckOut(Long id){
        log.info("Eliminando check-out con ID -> {}", id);
        if (!checkOutRepository.existsById(id)){
            throw new EntityNotFoundException("No se ha encontrado check-out con ID ->" + id);
        }
        checkOutRepository.deleteById(id);
    }
}