package cl.duoc.dsy1103.check_out.mapper;

import org.springframework.stereotype.Component;

import cl.duoc.dsy1103.check_out.dto.CheckOutRequest;
import cl.duoc.dsy1103.check_out.dto.CheckOutResponse;
import cl.duoc.dsy1103.check_out.model.CheckOut;

@Component
public class CheckOutMapper {
    public CheckOut fromRequest(CheckOutRequest request){
        return CheckOut.builder()
                .fechaSalida(request.getFechaSalida())
                .idReserva(request.getIdReserva())
                .idEmpleado(request.getIdEmpleado())
                .observaciones(request.getObservaciones())
                .build();
    }

    public CheckOutResponse toResponse(CheckOut checkOut){
        return CheckOutResponse.builder()
                .id(checkOut.getId())
                .fechaSalida(checkOut.getFechaSalida())
                .idReserva(checkOut.getIdReserva())
                .idEmpleado(checkOut.getIdEmpleado())
                .observaciones(checkOut.getObservaciones())
                .build();
    }
}
