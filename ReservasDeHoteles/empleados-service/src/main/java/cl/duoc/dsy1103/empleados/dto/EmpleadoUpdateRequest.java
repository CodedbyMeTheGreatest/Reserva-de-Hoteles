package cl.duoc.dsy1103.empleados.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoUpdateRequest {
    @Size(max = 12, message = "El run debe tener máximo 12 caracteres")
    private String run;

    @Size(max = 100, message = "El nombre completo debe tener máximo 100 caracteres")
    private String nombreCompleto;

    @Size(max = 50, message = "El cargo debe tener máximo 50 caracteres")
    private String cargo;

    private Long idHotel;
}
