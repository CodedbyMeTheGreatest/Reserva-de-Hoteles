package cl.duoc.dsy1103.facturas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaRequest {
    @NotBlank(message = "El folio es obligatorio")
    private String folio;

    @NotNull(message = "La id de la reserva es obligatoria")
    private Long idReserva;

    @NotNull(message = "El id de pago es obligatorio")
    private Long idPago;

    @NotBlank(message = "El run del huésped es obligatorio")
    private String runHuesped;

    @NotBlank(message = "La descripción de la habitación es obligatoria")
    private String descripcionHabitacion;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "La fecha de la factura es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser fututa")
    private LocalDateTime fechaFactura;

}
