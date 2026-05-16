package cl.duoc.dsy1103.reservas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReservaRequest {

    @NotNull(message = "El ID de la habitación es obligatorio")
    private Long idHabitacion;

    @NotNull(message = "El ID del huésped es obligatorio")    
    private Long idHuesped;

    @NotNull(message = "El ID del empleado es obligatorio")
    private Long idEmpleado;

    @NotNull(message = "La cantidad de días es obligatoria")
    private Integer cantDias;

    @NotNull(message = "El ID del check-in es obligatorio")
    private Long idCheckIn;

    private Long idCheckOut;
}
