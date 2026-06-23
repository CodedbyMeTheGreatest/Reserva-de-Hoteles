package cl.duoc.dsy1103.huespedes.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HuespedUpdateRequest {
    @Size(min = 4, max = 10, message = "El run debe tener entre 4 y 10 caracteres")
    private String run;

    @Size(min = 10, max = 150, message = "El nombre debe tener entre 10 y 150 caracteres")
    private String nombreCompleto;

    @Size(min = 10, max = 150, message = "El email debe tener entre 10 y 150 caracteres")
    private String email;

    @Size(min = 8, max = 20, message = "El teléfono debe tener entre 8 y 20 caracteres")
    private String telefono;

    @Size(min = 4, max = 50, message = "La nacionalidad debe tener entre 4 y 50 caracteres")
    private String nacionalidad;
}
