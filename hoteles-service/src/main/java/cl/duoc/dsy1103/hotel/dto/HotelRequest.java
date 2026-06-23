package cl.duoc.dsy1103.hotel.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

    @Size(max = 12, message = "El RUT no puede tener más de 12 caracteres")
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
}
