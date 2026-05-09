package cl.duoc.dsy1103.habitaciones.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitacionUpdateRequest {

    @Size(min = 1, max = 10, message = "El número de habitación debe tener entre 1 y 10 caracteres")
    private String numero;

    @Size(min = 1, max = 50, message = "La descripción debe tener entre 1 y 50 caracteres")
    private String descripcion;

    private Integer precioPorNoche;

    private Long idHotel;

    private Long idDisponibilidad;

}
