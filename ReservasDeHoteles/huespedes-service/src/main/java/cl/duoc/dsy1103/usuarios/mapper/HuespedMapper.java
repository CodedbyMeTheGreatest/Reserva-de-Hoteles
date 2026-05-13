package cl.duoc.dsy1103.usuarios.mapper;

import org.springframework.stereotype.Component;

import cl.duoc.dsy1103.usuarios.dto.HuespedRequest;
import cl.duoc.dsy1103.usuarios.dto.HuespedResponse;
import cl.duoc.dsy1103.usuarios.model.Huesped;

@Component
public class HuespedMapper {
    public Huesped fromRequest(HuespedRequest request){
        return Huesped.builder()
                .run(request.getRun())
                .nombreCompleto(request.getNombreCompleto())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .nacionalidad(request.getNacionalidad())
                .build();
    }


    public HuespedResponse toResponse(Huesped huesped){
        return HuespedResponse.builder()
                .run(huesped.getRun())
                .nombreCompleto(huesped.getNombreCompleto())
                .email(huesped.getEmail())
                .telefono(huesped.getTelefono())
                .nacionalidad(huesped.getNacionalidad())
                .build();
    }



}
