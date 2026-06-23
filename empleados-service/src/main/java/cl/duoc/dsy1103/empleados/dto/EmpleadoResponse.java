package cl.duoc.dsy1103.empleados.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoResponse extends RepresentationModel<EmpleadoResponse>{
    private Long idEmpleado;
    private String run;
    private String nombreCompleto;
    private String cargo;
    private Long idHotel;
    private String nombreHotel;
}
