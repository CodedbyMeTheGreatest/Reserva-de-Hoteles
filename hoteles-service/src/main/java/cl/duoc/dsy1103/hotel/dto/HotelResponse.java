package cl.duoc.dsy1103.hotel.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponse extends RepresentationModel<HotelResponse> {
    private Long idHotel;
    private String rut;
    private String direccion;
    private String nombre;
    
}
