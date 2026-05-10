package cl.duoc.dsy1103.pagos.dto;

import java.time.LocalDateTime;

import cl.duoc.dsy1103.pagos.enums.EstadoPago;
import cl.duoc.dsy1103.pagos.enums.MetodoPago;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoUpdateRequest {

    private MetodoPago metodoPago;
    private EstadoPago estadoPago;   

}
