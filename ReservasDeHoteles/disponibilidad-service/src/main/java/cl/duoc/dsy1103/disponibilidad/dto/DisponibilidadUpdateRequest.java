package cl.duoc.dsy1103.disponibilidad.dto;

import java.time.LocalDateTime;

import cl.duoc.dsy1103.disponibilidad.enums.EstadoDisponibilidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadUpdateRequest {

    private EstadoDisponibilidad estado;
    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;
}
