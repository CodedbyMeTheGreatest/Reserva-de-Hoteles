package cl.duoc.dsy1103.reservas.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaUpdateRequest {

    private Long idHabitacion;
    private Long idHuesped;
    private Long idEmpleado;
    private Integer cantDias;
}
