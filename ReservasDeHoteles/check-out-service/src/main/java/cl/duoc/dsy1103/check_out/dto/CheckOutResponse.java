package cl.duoc.dsy1103.check_out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckOutResponse {
    private Long id;
    private Date fechaSalida;
    private Long idReserva;
    private Long idEmpleado;
    private String observaciones;
}

