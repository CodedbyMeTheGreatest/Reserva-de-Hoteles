package cl.duoc.dsy1103.disponibilidad.dto;

import java.time.LocalDateTime;

import cl.duoc.dsy1103.disponibilidad.enums.EstadoDisponibilidad;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadRequest {


    @NotNull(message = "El estado de disponibilidad es obligatorio")
    private EstadoDisponibilidad estado;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaDesde;

    private LocalDateTime fechaHasta;
}
