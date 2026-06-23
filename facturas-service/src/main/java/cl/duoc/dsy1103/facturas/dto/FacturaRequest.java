package cl.duoc.dsy1103.facturas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaRequest {
    @NotBlank(message = "El folio es obligatorio")
    @Size(min = 3, max=5, message = "El folio debe tener entre 3 y 5 caracteres")
    private String folio;

    @NotNull(message = "La id de la reserva es obligatoria")
    private Long idReserva;

    @NotNull(message = "El id de pago es obligatorio")
    private Long idPago;

    @NotBlank(message = "El run del huésped es obligatorio")
    @Size(min = 4, max = 10, message = "El run debe tener máximo 4 y 10 caracteres")
    private String runHuesped;

    @NotNull(message = "El id de check in es obligatorio")
    private Long idCheckIn;
    @NotNull(message = "El id de check out es obligatorio")
    private Long idCheckOut;

    @NotBlank(message = "La descripción de la habitación es obligatoria")
    @Size(min = 4, max = 50, message = "La descripción debe tener entre 4 y 50 caracteres")
    private String descripcionHabitacion;
}
