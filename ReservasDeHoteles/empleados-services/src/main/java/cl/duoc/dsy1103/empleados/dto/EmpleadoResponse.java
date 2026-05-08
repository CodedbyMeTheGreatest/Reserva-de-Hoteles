package cl.duoc.dsy1103.empleados.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoResponse {
    private Long idEmpleado;
    private String run;
    private String nombreCompleto;
    private String cargo;
    private Long idHotel;
}
