package cl.duoc.dsy1103.check_in.mapper;

import cl.duoc.dsy1103.check_in.dto.CheckInRequest;
import cl.duoc.dsy1103.check_in.dto.CheckInResponse;
import cl.duoc.dsy1103.check_in.model.CheckIn;
import org.springframework.stereotype.Component;

@Component
public class CheckInMapper {
    public CheckIn fromRequest(CheckInRequest request){
        return CheckIn.builder()
                .idReserva(request.getIdReserva())
                .idEmpleado(request.getIdEmpleado())
                .fechaIngreso(request.getFechaIngreso())
                .observaciones(request.getObservaciones())
                .build();
    }

    public CheckInResponse toResponse(CheckIn checkIn){
        return CheckInResponse.builder()
                .id(checkIn.getId())
                .idReserva(checkIn.getIdReserva())
                .idEmpleado(checkIn.getIdEmpleado())
                .fechaIngreso(checkIn.getFechaIngreso())
                .observaciones(checkIn.getObservaciones())
                .build();
    }
}
