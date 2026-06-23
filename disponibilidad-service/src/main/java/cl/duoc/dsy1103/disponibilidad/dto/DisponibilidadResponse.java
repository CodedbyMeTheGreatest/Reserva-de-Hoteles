package cl.duoc.dsy1103.disponibilidad.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import cl.duoc.dsy1103.disponibilidad.enums.EstadoDisponibilidad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisponibilidadResponse extends RepresentationModel<DisponibilidadResponse> {
    private Long idDisponibilidad;
    private EstadoDisponibilidad estado;
    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;
}
