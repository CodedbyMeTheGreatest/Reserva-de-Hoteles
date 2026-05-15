package cl.duoc.dsy1103.reservas.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.dsy1103.reservas.dto.ReservaRequest;
import cl.duoc.dsy1103.reservas.dto.ReservaResponse;
import cl.duoc.dsy1103.reservas.dto.ReservaUpdateRequest;
import cl.duoc.dsy1103.reservas.mapper.ReservaMapper;
import cl.duoc.dsy1103.reservas.model.Reserva;
import cl.duoc.dsy1103.reservas.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaMapper reservaMapper;

    public List<ReservaResponse> buscarReservas() {
        log.info("Buscando reservas...");
        return reservaRepository.findAll().stream()
                .map(reservaMapper::toResponse)
                .toList();
    }

    public ReservaResponse buscarReservaPorId(Long idReserva) {
        log.info("Buscando reserva por ID: {}", idReserva);
        return reservaRepository.findById(idReserva)
                .map(reservaMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada."));
    }

    public ReservaResponse crearReserva(ReservaRequest request) {
        log.info("Creando nueva reserva para habitación ID: {}", request.getIdHabitacion());
        Reserva reserva = reservaRepository.save(reservaMapper.fromRequest(request));
        return reservaMapper.toResponse(reserva);
    }

    public ReservaResponse actualizarReserva(Long idReserva, ReservaUpdateRequest request) {
        log.info("Actualizando reserva ID: {}", idReserva);
        Reserva reservaExistente = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada."));
        
        if (request.getIdHabitacion() != null) {
            reservaExistente.setIdHabitacion(request.getIdHabitacion());
        }
        if (request.getIdHuesped() != null) {
            reservaExistente.setIdHuesped(request.getIdHuesped());
        }
        if (request.getIdEmpleado() != null) {
            reservaExistente.setIdEmpleado(request.getIdEmpleado());
        }
        if (request.getCantDias() != null) {
            reservaExistente.setCantDias(request.getCantDias());
        }
        if (request.getIdCheckIn() != null) {
            reservaExistente.setIdCheckIn(request.getIdCheckIn());
        }
        if (request.getIdCheckOut() != null) {
            reservaExistente.setIdCheckOut(request.getIdCheckOut());
        }
        
        Reserva reservaActualizada = reservaRepository.save(reservaExistente);
        return reservaMapper.toResponse(reservaActualizada);
    }

    public void eliminarReserva(Long idReserva) {
        log.info("Eliminando reserva por ID: {}", idReserva);
        if(!reservaRepository.existsById(idReserva)) {
            throw new NoSuchElementException("Reserva no encontrada.");
        }
        reservaRepository.deleteById(idReserva);
    }

    //haz que busque reservas por run de empleado :) i need it bro

}
