package cl.duoc.dsy1103.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoResponse {
    private Long idEmpleado;
    private String run;
    private String nombreCompleto;
    private String cargo;
    private Long idHotel;
}
