package cl.duoc.dsy1103.habitaciones.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitacionRequest {

    @Size(min = 1, max = 10, message = "El número de habitación debe tener entre 1 y 10 caracteres")
    @NotNull(message = "El número de habitación es obligatorio")
    private String numero;

    @Size(min = 1, max = 200, message = "La descripción debe tener entre 1 y 200 caracteres")
    @NotNull(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio por noche es obligatorio")
    private Integer precioPorNoche;

    @NotNull(message = "El ID del hotel es obligatorio")
    private Long idHotel;

    @NotNull(message = "El ID de disponibilidad es obligatorio")
    private Long idDisponibilidad;

}
