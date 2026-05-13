package cl.duoc.dsy1103.usuarios.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuespedRequest {
    
    @NotNull(message = "El ID del huésped es obligatorio")
    private Long id;

    @NotNull(message = "El run del huésped es obligatorio")
    private String run;

    @NotNull(message = "El nombre del huésped es obligatorio")
    private String nombreCompleto;

    @NotNull(message = "El email del huésped es obligatorio")
    private String email;

    @NotNull(message = "El teléfono del huésped es obligatorio")
    private int telefono;

   @NotNull(message = "La nacionalidad del huésped es obligatoria")
    private String nacionalidad;



}
