package cl.duoc.dsy1103.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaResponse {
    private Long idReserva;

    private Long idHabitacion;
    private Long idHuesped;
    private Long idEmpleado;

    private String cantDias;
    private Long idCheckIn;
    private Long idCheckOut;
}
