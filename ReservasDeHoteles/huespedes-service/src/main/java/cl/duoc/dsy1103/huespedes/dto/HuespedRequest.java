package cl.duoc.dsy1103.huespedes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuespedRequest {
    @NotBlank(message = "El run del huésped es obligatorio")
    @Size(min = 4, max = 10, message = "El run debe tener entre 4 y 10 caracteres")
    private String run;

    @NotBlank(message = "El nombre del huésped es obligatorio")
    @Size(min = 10, max = 150, message = "El nombre debe tener entre 10 y 150 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El email del huésped es obligatorio")
    @Size(min = 10, max = 150, message = "El email debe tener entre 10 y 150 caracteres")
    private String email;

    @NotBlank(message = "El teléfono del huésped es obligatorio")
    @Size(min = 8, max = 20, message = "El teléfono debe tener entre 8 y 20 caracteres")
    private String telefono;

    @NotBlank(message = "La nacionalidad del huésped es obligatoria")
    @Size(min = 4, max = 50, message = "La nacionalidad debe tener entre 4 y 50 caracteres")
    private String nacionalidad;
}
