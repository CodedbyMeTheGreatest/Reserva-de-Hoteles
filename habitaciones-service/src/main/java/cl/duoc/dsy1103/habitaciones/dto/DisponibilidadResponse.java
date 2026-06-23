package cl.duoc.dsy1103.habitaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadResponse {
    private Long id;
    private String estado;
    private String fechaDesde;
    private String fechaHasta;
    
}
