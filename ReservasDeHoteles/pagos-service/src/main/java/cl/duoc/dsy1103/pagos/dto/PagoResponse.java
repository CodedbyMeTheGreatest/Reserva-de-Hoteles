package cl.duoc.dsy1103.pagos.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import cl.duoc.dsy1103.pagos.enums.EstadoPago;
import cl.duoc.dsy1103.pagos.enums.MetodoPago;
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
    private BigInteger precioPorNoche;
    private Integer cantDias;
    private BigInteger subtotal;
    private BigInteger impuestos;
    private BigInteger total;
    private MetodoPago metodoPago;
    private EstadoPago estadoPago;
    private LocalDateTime fechaPago;
}
