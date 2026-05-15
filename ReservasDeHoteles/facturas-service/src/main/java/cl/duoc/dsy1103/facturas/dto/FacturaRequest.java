package cl.duoc.dsy1103.facturas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaRequest {
    @NotBlank(message = "El folio es obligatorio")
    @Size(min = 4, max=30, message = "El folio debe tener entre 4 y 30 caracteres")
    private String folio;

    @NotNull(message = "La id de la reserva es obligatoria")
    private Long idReserva;

    @NotNull(message = "El id de pago es obligatorio")
    private Long idPago;

    @NotBlank(message = "El run del huésped es obligatorio")
    @Size(min = 9, max = 12, message = "El run debe tener entre 9 y 12 caracteres")
    private String runHuesped;

    @NotBlank(message = "La descripción de la habitación es obligatoria")
    @Size(max = 250, message = "La descripción debe tener máximo 250 caracteres")
    private String descripcionHabitacion;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado debe tener máximo 50 caracteres")
    private String estado;

    @NotNull(message = "La fecha de la factura es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser fututa")
    private LocalDateTime fechaFactura;

}
