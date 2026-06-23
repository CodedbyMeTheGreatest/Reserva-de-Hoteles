package cl.duoc.dsy1103.check_in.dto;

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
public class CheckInResponse extends RepresentationModel<CheckInResponse>{
    private Long id;
    private Long idReserva;
    private Long idEmpleado;
    private LocalDateTime fechaIngreso;
    private String observaciones;
}
