package cl.duoc.dsy1103.check_in.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInUpdateRequest {
    private Long idReserva;
    private Long idEmpleado;

    @Size(max = 250, message = "Las observaciones pueden ser de maximo 250 caracteres")
    private String observaciones;
}
