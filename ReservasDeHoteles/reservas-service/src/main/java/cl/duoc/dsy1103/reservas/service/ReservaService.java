package cl.duoc.dsy1103.reservas.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.dsy1103.reservas.client.CheckinClient;
import cl.duoc.dsy1103.reservas.client.CheckoutClient;
import cl.duoc.dsy1103.reservas.client.EmpleadoClient;
import cl.duoc.dsy1103.reservas.client.HabitacionClient;
import cl.duoc.dsy1103.reservas.client.HuespedClient;
import cl.duoc.dsy1103.reservas.dto.EmpleadoResponse;
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

    @Autowired
    private CheckinClient checkinClient;
    @Autowired
    private CheckoutClient checkoutClient;
    @Autowired
    private EmpleadoClient empleadoClient;
    @Autowired
    private HuespedClient huespedClient;
    @Autowired
    private HabitacionClient habitacionClient;

    public List<ReservaResponse> buscarReservas() {
        log.info("Buscando reservas...");
        return reservaRepository.findAll().stream()
                .map(reservaMapper::toResponse)
                .toList();
    }

    public List<ReservaResponse> buscarReservasPorEmpleado(String runEmpleado) {
        log.info("Buscando reservas para empleado RUN: {}", runEmpleado);
        EmpleadoResponse empleado = empleadoClient.buscarEmpleadoPorRun(runEmpleado);
        return reservaRepository.findByIdEmpleado(empleado.getIdEmpleado()).stream()
                .map(reservaMapper::toResponse)
                .toList();
    }

    public ReservaResponse buscarReservaPorId(Long idReserva) {
        log.info("Buscando reserva por ID: {}", idReserva);
        return reservaRepository.findById(idReserva)
                .map(reservaMapper::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada."));
    }

    public ReservaResponse crearReserva(ReservaRequest request) {
        log.info("Creando nueva reserva para habitación ID: {}", request.getIdHabitacion());
        huespedClient.buscarHuespedPorId(request.getIdHuesped());
        empleadoClient.buscarEmpleadoPorId(request.getIdEmpleado());
        checkinClient.buscarCheckInPorId(request.getIdCheckIn());
        checkoutClient.buscarCheckOutPorId(request.getIdCheckOut());
        habitacionClient.buscarHabitacionPorId(request.getIdHabitacion());
        Reserva reserva = reservaRepository.save(reservaMapper.fromRequest(request));
        return reservaMapper.toResponse(reserva);
    }

    public ReservaResponse actualizarReserva(Long idReserva, ReservaUpdateRequest request) {
        log.info("Actualizando reserva ID: {}", idReserva);
        Reserva reservaExistente = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new NoSuchElementException("Reserva no encontrada."));
        
        if (request.getIdHabitacion() != null) {
            habitacionClient.buscarHabitacionPorId(request.getIdHabitacion());
            reservaExistente.setIdHabitacion(request.getIdHabitacion());
        }
        if (request.getIdHuesped() != null) {
            huespedClient.buscarHuespedPorId(request.getIdHuesped());
            reservaExistente.setIdHuesped(request.getIdHuesped());
        }
        if (request.getIdEmpleado() != null) {
            empleadoClient.buscarEmpleadoPorId(request.getIdEmpleado());
            reservaExistente.setIdEmpleado(request.getIdEmpleado());
        }
        if (request.getCantDias() != null) {
            reservaExistente.setCantDias(request.getCantDias());
        }
        if (request.getIdCheckIn() != null) {
            checkinClient.buscarCheckInPorId(request.getIdCheckIn());
            reservaExistente.setIdCheckIn(request.getIdCheckIn());
        }
        if (request.getIdCheckOut() != null) {
            checkoutClient.buscarCheckOutPorId(request.getIdCheckOut());
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

}
