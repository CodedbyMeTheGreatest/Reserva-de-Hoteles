package cl.duoc.dsy1103.reservas.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaUpdateRequest {

    private Long id;
    private Long idHabitacion;
    private Long idHuesped;
    private Long idEmpleado;

    @Size(min = 1, max = 15, message = "La cantidad de días debe tener entre 1 y 15 caracteres")
    private String cantDias;
    
    private Long idCheckIn;
    private Long idCheckOut;

}
