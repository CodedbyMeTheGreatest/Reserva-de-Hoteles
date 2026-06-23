package cl.duoc.dsy1103.hotel.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelUpdateRequest {

    @Size(max = 12, message = "El RUT no puede tener más de 12 caracteres")
    private String rut;

    @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
    private String direccion;

    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;    

}
