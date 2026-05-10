package cl.duoc.dsy1103.facturas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaResponse {
    private Long id;
    private String folio;
    private Long idReserva;
    private String runHuesped;
    private LocalDateTime fecha;
    private Long idPago;
    private String descripcionHabitacion;
    private String estado;

}

