package cl.duoc.dsy1103.pagos.dto;

import java.math.BigInteger;

import cl.duoc.dsy1103.pagos.enums.MetodoPago;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequest {

    @NotNull(message = "El ID de la habitación es obligatorio")
    private Long idHabitacion;

    @NotNull(message = "El ID del huésped es obligatorio")
    private Long idHuesped;

    @NotNull(message = "El precio por noche es obligatorio")
    private BigInteger precioPorNoche;

    @NotNull(message = "La cantidad de días es obligatoria")
    private Integer cantDias;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;
}
