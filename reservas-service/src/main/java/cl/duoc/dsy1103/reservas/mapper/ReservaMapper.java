package cl.duoc.dsy1103.reservas.mapper;

import org.springframework.stereotype.Component;

import cl.duoc.dsy1103.reservas.dto.ReservaRequest;
import cl.duoc.dsy1103.reservas.dto.ReservaResponse;
import cl.duoc.dsy1103.reservas.model.Reserva;

@Component
public class ReservaMapper {

    public Reserva fromRequest (ReservaRequest request) {
        return Reserva.builder()
                .idHabitacion(request.getIdHabitacion())
                .idHuesped(request.getIdHuesped())
                .idEmpleado(request.getIdEmpleado())
                .cantDias(request.getCantDias())
                .build();
    }

    public ReservaResponse toResponse (Reserva reserva) {
        return ReservaResponse.builder()
                .idReserva(reserva.getIdReserva())
                .idHabitacion(reserva.getIdHabitacion())
                .idHuesped(reserva.getIdHuesped())
                .idEmpleado(reserva.getIdEmpleado())
                .cantDias(reserva.getCantDias())
                .build();
    }
}
