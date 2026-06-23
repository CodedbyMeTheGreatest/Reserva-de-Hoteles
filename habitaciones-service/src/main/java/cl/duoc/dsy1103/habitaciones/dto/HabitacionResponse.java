package cl.duoc.dsy1103.habitaciones.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HabitacionResponse extends RepresentationModel<HabitacionResponse> {
    private Long idHabitacion;
    private String numero;
    private String descripcion;
    private Integer precioPorNoche;
    private Long idHotel;
    private Long idDisponibilidad;

}
