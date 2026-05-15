package cl.duoc.dsy1103.check_in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInResponse {
    private Long id;
    private LocalDateTime fechaIngreso;
    private Long idReserva;
    private Long idEmpleado;
    private String observaciones;
}
