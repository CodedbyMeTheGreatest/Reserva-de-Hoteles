package cl.duoc.dsy1103.empleados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequest {
    @NotBlank(message = "El run es obligatorio")
    private String run;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "El cargo  es obligatorio")
    private String cargo;

    @NotNull(message = "El id del hotel es obligatorio")
    private Long idHotel;
}
