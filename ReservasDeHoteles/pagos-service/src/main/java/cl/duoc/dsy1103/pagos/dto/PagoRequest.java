package cl.duoc.dsy1103.pagos.dto;

import java.time.LocalDateTime;

import cl.duoc.dsy1103.pagos.enums.MetodoPago;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 15, message = "El precio por noche debe tener entre 1 y 15 caracteres")
    @NotNull(message = "El precio por noche es obligatorio")
    private Integer precioPorNoche;

    @Size(min = 1, max = 15, message = "La cantidad de días debe tener entre 1 y 15 caracteres")
    @NotNull(message = "La cantidad de días es obligatoria")
    private Integer cantDias;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;
}
