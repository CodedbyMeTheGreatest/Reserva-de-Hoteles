package cl.duoc.dsy1103.empleados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequest {

    @NotBlank(message = "El run es obligatorio")
    @Size(max = 12, message = "El run debe tener máximo 12 caracteres")
    private String run;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre completo debe tener máximo 100 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El cargo  es obligatorio")
    @Size(max = 50, message = "El cargo debe tener máximo 50 caracteres")
    private String cargo;

    @NotNull(message = "El id del hotel es obligatorio")
    private Long idHotel;
}
