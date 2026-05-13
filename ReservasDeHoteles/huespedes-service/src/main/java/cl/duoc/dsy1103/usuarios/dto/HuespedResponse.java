package cl.duoc.dsy1103.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HuespedResponse {

    private Long id;
    private String run;
    private String nombreCompleto;
    private String email;
    private int telefono;
    private String nacionalidad;


}
