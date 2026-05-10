package cl.duoc.dsy1103.habitaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitacionResponse {
    private Long idHabitacion;
    private String numero;
    private String descripcion;
    private Integer precioPorNoche;
    private Long idHotel;
    private Long idDisponibilidad;

}
