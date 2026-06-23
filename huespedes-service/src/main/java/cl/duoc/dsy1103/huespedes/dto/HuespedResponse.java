package cl.duoc.dsy1103.huespedes.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HuespedResponse extends RepresentationModel<HuespedResponse>{
    private Long id;
    private String run;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String nacionalidad;
}
