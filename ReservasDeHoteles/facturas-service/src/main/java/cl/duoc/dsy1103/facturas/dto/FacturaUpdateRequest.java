package cl.duoc.dsy1103.facturas.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaUpdateRequest {
    @Size(max=30, message = "El folio debe tener entre 4 y 30 caracteres")
    private String folio;

    private Long idReserva;

    private Long idPago;

    @Size(max = 12, message = "El run debe tener entre 9 y 12 caracteres")
    private String runHuesped;

    @Size(max = 250, message = "La descripción debe tener máximo 250 caracteres")
    private String descripcionHabitacion;

    @Size(max = 50, message = "El estado debe tener máximo 50 caracteres")
    private String estado;

    @PastOrPresent(message = "La fecha no puede ser fututa")
    private LocalDateTime fechaFactura;
}
