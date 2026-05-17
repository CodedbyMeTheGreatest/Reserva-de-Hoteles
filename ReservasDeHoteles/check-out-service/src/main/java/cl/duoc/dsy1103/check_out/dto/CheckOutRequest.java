package cl.duoc.dsy1103.check_out.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutRequest {
    @NotNull(message = "La ID de la reserva es obligatoria")
    private Long idReserva;

    @NotNull(message = "La ID del empleado es obligatoria")
    private Long idEmpleado;

    @Size(max = 250, message = "Las observaciones deben tener máximo 250 caracteres")
    private String observaciones;
}