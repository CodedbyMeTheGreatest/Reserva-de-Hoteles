package cl.duoc.dsy1103.check_out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckOutResponse extends RepresentationModel<CheckOutResponse>{
    private Long id;
    private LocalDateTime fechaSalida;
    private Long idReserva;
    private Long idEmpleado;
    private String observaciones;
}

