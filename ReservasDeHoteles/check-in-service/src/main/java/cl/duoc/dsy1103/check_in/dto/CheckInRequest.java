package cl.duoc.dsy1103.check_in.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRequest {
    @NotNull(message = "La ID de la reserva es obligatoria")
    private Long idReserva;

    @NotNull(message = "La ID del empleado es obligatoria")
    private Long idEmpleado;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no debe ser futura")
    private LocalDateTime fechaIngreso;

    private String observaciones;
}
