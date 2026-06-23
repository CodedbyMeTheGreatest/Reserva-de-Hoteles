package cl.duoc.dsy1103.disponibilidad.mapper;

import org.springframework.stereotype.Component;

import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadRequest;
import cl.duoc.dsy1103.disponibilidad.dto.DisponibilidadResponse;
import cl.duoc.dsy1103.disponibilidad.model.Disponibilidad;

@Component
public class DisponibilidadMapper {

    public Disponibilidad fromRequest(DisponibilidadRequest request){
        return Disponibilidad.builder()
                .estado(request.getEstado())
                .fechaDesde(request.getFechaDesde())
                .fechaHasta(request.getFechaHasta())
                .build();
    }

    public DisponibilidadResponse toResponse(Disponibilidad disponibilidad){
        return DisponibilidadResponse.builder()
                .idDisponibilidad(disponibilidad.getIdDisponibilidad())
                .estado(disponibilidad.getEstado())
                .fechaDesde(disponibilidad.getFechaDesde())
                .fechaHasta(disponibilidad.getFechaHasta())
                .build();
    }

}
