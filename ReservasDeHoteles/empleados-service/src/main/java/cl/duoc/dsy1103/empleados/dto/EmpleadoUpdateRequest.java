package cl.duoc.dsy1103.empleados.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoUpdateRequest {
    @Size(min = 4, max = 10, message = "El run debe tener entre 4 y 10 caracteres")
    private String run;

    @Size(min = 10, max = 150, message = "El nombre debe tener entre 10 y 150 caracteres")
    private String nombreCompleto;

    @Size(min = 1, max = 20, message = "El cargo debe tener entre 1 y 20 caracteres")
    private String cargo;
    private Long idHotel;
}
