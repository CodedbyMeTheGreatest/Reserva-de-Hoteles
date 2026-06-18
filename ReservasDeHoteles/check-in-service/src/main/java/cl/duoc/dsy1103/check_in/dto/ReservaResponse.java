package cl.duoc.dsy1103.check_in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponse {
    private Long id;
    private Long idHabitacion;
    private Long idHuesped;
    private Long idEmpleado;
    private int cantDias;
    private Long idCheckIn;
    private Long idCheckOut;
}
