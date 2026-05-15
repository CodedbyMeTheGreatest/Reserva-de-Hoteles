package cl.duoc.dsy1103.facturas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturaResponse {
    private Long id;
    private String folio;
    private Long idReserva;
    private Long idPago;
    private String runHuesped;

    private String nombreHuesped;

    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;

    private String descripcionHabitacion;

    private Integer cantDias;

    private Integer subtotal;
    private Integer impuestos;
    private Integer total;

    private String estado;
    private LocalDateTime fechaFactura;

}

