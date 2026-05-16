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
    @Size(max=5, message = "El folio debe tener entre 3 y 5 caracteres")
    private String folio;

    private Long idReserva;
    private Long idPago;

    @Size(min = 4, max = 10, message = "El run debe tener máximo 4 y 10 caracteres")
    private String runHuesped;

    @Size(min = 4, max = 50, message = "La descripción debe tener enter 4 y  50 caracteres")
    private String descripcionHabitacion;

    @Size(min = 4, max = 20, message = "El estado debe tener entre 4 y 20 caracteres")
    private String estado;

    @PastOrPresent(message = "La fecha no puede ser fututa")
    private LocalDateTime fechaFactura;
}
