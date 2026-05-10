package cl.duoc.dsy1103.facturas.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class PagoResponse {

    private Long idPago;
    private Long idHabitacion;
    private Long idHuesped;
    private Integer precioPorNoche;
    private Integer cantDias;
    private Integer subtotal;
    private Integer impuestos;
    private Integer total;
    private LocalDateTime fechaPago;
}
