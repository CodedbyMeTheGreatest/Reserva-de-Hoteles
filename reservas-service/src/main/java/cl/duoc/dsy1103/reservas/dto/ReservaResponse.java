package cl.duoc.dsy1103.reservas.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaResponse extends RepresentationModel<ReservaResponse> {
    private Long idReserva;

    private Long idHabitacion;
    private Long idHuesped;
    private Long idEmpleado;
    private Integer cantDias;

}
