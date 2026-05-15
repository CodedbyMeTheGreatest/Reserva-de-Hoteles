package cl.duoc.dsy1103.empleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoUpdateRequest {
    private String run;
    private String nombreCompleto;
    private String cargo;
    private Long idHotel;
}
