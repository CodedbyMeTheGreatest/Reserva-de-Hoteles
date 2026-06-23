package cl.duoc.dsy1103.pagos.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import cl.duoc.dsy1103.pagos.enums.EstadoPago;
import cl.duoc.dsy1103.pagos.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class PagoResponse extends RepresentationModel<PagoResponse> {

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
