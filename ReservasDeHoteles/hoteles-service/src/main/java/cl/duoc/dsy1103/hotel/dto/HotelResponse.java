package cl.duoc.dsy1103.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponse {
    private Long idHotel;
    private String rut;
    private String direccion;
    private String nombre;
    
}
